package com.f4w.weapp;

import cn.binarywang.wx.miniapp.bean.WxMaKefuMessage;
import com.f4w.entity.BusiApp;
import com.f4w.mapper.BusiAppMapper;
import com.f4w.mapper.BusiQuestionMapper;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizerInfo;
import me.chanjar.weixin.open.bean.message.WxOpenXmlMessage;
import me.chanjar.weixin.open.bean.result.WxOpenAuthorizerInfoResult;
import me.chanjar.weixin.open.bean.result.WxOpenQueryAuthResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("notify")
public class NotifyController {
    @Resource
    protected WxOpenService wxOpenService;
    @Resource
    private BusiAppMapper busiAppMapper;
    @Resource
    private BusiQuestionMapper busiQuestionMapper;
    @Resource
    private WxMpMessageRouter wxMpMessageRouter;

    @RequestMapping("authorizerRefreshToken")
    public void authorizerRefreshToken(
            @RequestParam("auth_code") String authCode
            , @RequestParam("expires_in") String expiresIn
            , @RequestParam("uid") Integer uid
            , HttpServletResponse response) throws IOException {
        log.info(
                "\n接收微信请求：[authCode=[{}], expiresIn=[{}], uid=[{}]",
                authCode, expiresIn, uid);
        try {
            WxOpenQueryAuthResult wxOpenQueryAuthResult = wxOpenService.getWxOpenComponentService().getQueryAuth(authCode);
            String appId = wxOpenQueryAuthResult.getAuthorizationInfo().getAuthorizerAppid();
            BusiApp busiApp = new BusiApp();
            busiApp.setAppId(appId);
            busiApp = busiAppMapper.selectOne(busiApp);
            if (null == busiApp) {
                //获取应用信息
                WxOpenAuthorizerInfoResult wxOpenAuthorizerInfoResult = wxOpenService.getWxOpenComponentService().getAuthorizerInfo(appId);
                WxOpenAuthorizerInfo wxOpenAuthorizerInfo = wxOpenAuthorizerInfoResult.getAuthorizerInfo();
                busiApp = new BusiApp();
                busiApp.setAppId(appId);
                busiApp.setNickName(wxOpenAuthorizerInfo.getNickName());
                busiApp.setHeadImg(wxOpenAuthorizerInfo.getHeadImg());
                busiApp.setServiceTypeInfo(wxOpenAuthorizerInfo.getServiceTypeInfo());
                busiApp.setVerifyTypeInfo(wxOpenAuthorizerInfo.getVerifyTypeInfo());
                busiApp.setUserName(wxOpenAuthorizerInfo.getUserName());
                busiApp.setPrincipalName(wxOpenAuthorizerInfo.getPrincipalName());
                busiApp.setQrcodeUrl(wxOpenAuthorizerInfo.getQrcodeUrl());
                busiApp.setSignature(wxOpenAuthorizerInfo.getSignature());
                busiApp.setStatus(1);
                busiApp.setReplay(0);
                busiApp.setUid(uid);
                busiApp.setAutoMessage(0);
                if (null == wxOpenAuthorizerInfo.getMiniProgramInfo()) {
                    busiApp.setMiniProgramInfo(1);
                } else {
                    busiApp.setMiniProgramInfo(2);
                }
                busiAppMapper.insertSelective(busiApp);
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/index.html#/busi/busiApp");
    }

    @RequestMapping("message")
    public Object receiveTicket(@RequestBody(required = false) String requestBody, @RequestParam("timestamp") String timestamp,
                                @RequestParam("nonce") String nonce, @RequestParam("signature") String signature,
                                @RequestParam(name = "encrypt_type", required = false) String encType,
                                @RequestParam(name = "msg_signature", required = false) String msgSignature) {
        log.info(
                "\n接收微信请求：[signature=[{}], encType=[{}], msgSignature=[{}],"
                        + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                signature, encType, msgSignature, timestamp, nonce, requestBody);

        if (!StringUtils.equalsIgnoreCase("aes", encType) || !wxOpenService.getWxOpenComponentService().checkSignature(timestamp, nonce, signature)) {
            log.error("非法请求");
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }

        // aes加密的消息
        WxOpenXmlMessage inMessage = WxOpenXmlMessage.fromEncryptedXml(requestBody, wxOpenService.getWxOpenConfigStorage(), timestamp, nonce, msgSignature);
        log.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
        String out = null;
        try {
            out = wxOpenService.getWxOpenComponentService().route(inMessage);
        } catch (WxErrorException e) {
            log.error("error");
        }

        log.debug("\n组装回复信息：{}", out);

        return out;
    }

    @RequestMapping("{appId}/callback")
    public Object callback(@RequestBody(required = false) String requestBody,
                           @PathVariable("appId") String appId,
                           @RequestParam("signature") String signature,
                           @RequestParam("timestamp") String timestamp,
                           @RequestParam("nonce") String nonce,
                           @RequestParam("openid") String openid,
                           @RequestParam("encrypt_type") String encType,
                           @RequestParam("msg_signature") String msgSignature) {
        log.info(
                "\n接收微信请求：[appId=[{}], openid=[{}], signature=[{}], encType=[{}], msgSignature=[{}],"
                        + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                appId, openid, signature, encType, msgSignature, timestamp, nonce, requestBody);
        if (!StringUtils.equalsIgnoreCase("aes", encType) || !wxOpenService.getWxOpenComponentService().checkSignature(timestamp, nonce, signature)) {
            log.error("非法请求");
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }
        log.info("开始路由");
        String out = "";
        WxMpXmlMessage inMessage = WxOpenXmlMessage.fromEncryptedMpXml(requestBody,
                wxOpenService.getWxOpenConfigStorage(), timestamp, nonce, msgSignature);
        WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage, new HashMap<>(), wxOpenService.getWxOpenComponentService().getWxMpServiceByAppid(appId));
        if (outMessage != null) {
            out = WxOpenXmlMessage.wxMpOutXmlMessageToEncryptedXml(outMessage, wxOpenService.getWxOpenConfigStorage());
        }

        log.info("返回消息内容22---{}", out);
        return out;
    }

}
