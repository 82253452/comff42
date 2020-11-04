package com.f4w.weapp.handler;

import com.f4w.entity.BusiApp;
import com.f4w.mapper.BusiAppMapper;
import com.f4w.weapp.WxOpenService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.open.bean.result.WxOpenResult;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class AuditSuccessHandler implements WxMpMessageHandler {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private BusiAppMapper busiAppMapper;
    @Resource
    protected WxOpenService wxOpenService;


    @Override
    public WxMpXmlOutMessage handle(
            WxMpXmlMessage wxMessage,
            Map<String, Object> context,
            WxMpService wxMpService,
            WxSessionManager sessionManager) throws WxErrorException {
        BusiApp busiApp = new BusiApp();
        busiApp.setAppId(wxMpService.getWxMpConfigStorage().getAppId());
        busiApp = busiAppMapper.selectOne(busiApp);
        busiApp.setStatus(3);
        busiApp.setAuditMsg("审核通过");
        busiAppMapper.updateByPrimaryKey(busiApp);
        WxOpenResult wxOpenResult = wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(wxMpService.getWxMpConfigStorage().getAppId()).releaesAudited();
        if (!wxOpenResult.isSuccess()) {
            busiApp.setStatus(6);
            busiApp.setAuditMsg(wxOpenResult.getErrmsg());
        } else {
            busiApp.setStatus(5);
            busiApp.setAuditMsg("发布成功");
            setDomain(wxMpService.getWxMpConfigStorage().getAppId());
        }
        busiAppMapper.updateByPrimaryKey(busiApp);
        return null;
    }

    public void setDomain(String appId) throws WxErrorException {
        String domain = "https://www.cxduo.com";
        String domain2 = "https://mass.zhihuizhan.net";
        List<String> webViewDomain = new ArrayList<>();
        webViewDomain.add(domain2);
        List<String> requestdomainList = new ArrayList<>();
        requestdomainList.add(domain);
        requestdomainList.add(domain2);
        List<String> wsrequestdomainList = new ArrayList<>();
        wsrequestdomainList.add(domain);
        wsrequestdomainList.add(domain2);
        List<String> uploaddomainList = new ArrayList<>();
        uploaddomainList.add(domain);
        uploaddomainList.add(domain2);
        List<String> downloaddomainList = new ArrayList<>();
        downloaddomainList.add(domain);
        downloaddomainList.add(domain2);
        wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(appId).setWebViewDomain("add", webViewDomain);
        wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(appId).modifyDomain("add", requestdomainList, wsrequestdomainList, uploaddomainList, downloaddomainList);
    }
}
