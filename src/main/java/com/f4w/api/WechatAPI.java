package com.f4w.api;


import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.f4w.annotation.NotTokenIntecerpt;
import com.f4w.dto.req.AlertBodyReq;
import com.f4w.dto.req.AlertHttpBodyReq;
import com.f4w.dto.req.JobInfoReq;
import com.f4w.entity.BusiApp;
import com.f4w.entity.BusiAppPage;
import com.f4w.entity.BusiArticle;
import com.f4w.entity.SysUser;
import com.f4w.job.WechatPushArticleJob;
import com.f4w.mapper.BusiAppMapper;
import com.f4w.mapper.BusiAppPageMapper;
import com.f4w.mapper.BusiArticleMapper;
import com.f4w.mapper.SysUserMapper;
import com.f4w.service.WechatService;
import com.f4w.utils.JWTUtils;
import com.f4w.utils.R;
import com.f4w.utils.ShowException;
import com.f4w.weapp.WxOpenService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

import static com.f4w.utils.Constant.Cachekey.ALERT_MESSAGE_APPID;
import static com.f4w.utils.Constant.Cachekey.SEND_MESSAGE_OPENID;

@Slf4j
@RestController
@RequestMapping("/we")
@NotTokenIntecerpt
public class WechatAPI {
    @Resource
    public BusiAppPageMapper busiAppPageMapper;
    @Resource
    public BusiAppMapper busiAppMapper;
    @Resource
    public BusiArticleMapper busiArticleMapper;
    @Resource
    public SysUserMapper sysUserMapper;
    @Resource
    private JWTUtils jwtUtils;
    @Resource
    private WxOpenService wxOpenService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private WechatPushArticleJob wechatPushArticleJob;
    @Resource
    private WechatService wechatService;

    @RequestMapping("login")
    public R login(@RequestParam Map<String, String> map) {
        try {
            BusiApp busiApp = null;
            if (StringUtils.isNotBlank(map.get("appId"))) {
                busiApp = new BusiApp();
                busiApp.setAppId(map.get("appId"));
                busiApp = busiAppMapper.selectOne(busiApp);
                if (null == busiApp) {
                    return R.error("appid 不正确");
                }
            }

//            WxMaUserService wxMaUserService = WxAppUtils.getWxMaUserService(busiApp);
            WxMaUserService wxMaUserService = wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(busiApp.getAppId()).getUserService();
            WxMaJscode2SessionResult wxMaJscode2SessionResult = wxMaUserService.getSessionInfo(map.get("code"));
            WxMaUserInfo wxMaUserInfo = wxMaUserService.getUserInfo(wxMaJscode2SessionResult.getSessionKey(), map.get("encryptedData"), map.get("iv"));
            SysUser sysUser = new SysUser();
            sysUser.setOpenid(wxMaUserInfo.getOpenId());
            sysUser = sysUserMapper.selectOne(sysUser);
            if (sysUser != null) {
                sysUser.setGender(Integer.valueOf(wxMaUserInfo.getGender()));
                sysUser.setAvatarurl(wxMaUserInfo.getAvatarUrl());
                sysUserMapper.updateByPrimaryKeySelective(sysUser);
            } else {
                sysUser = new SysUser();
                sysUser.setAvatarurl(wxMaUserInfo.getAvatarUrl());
                sysUser.setNickname(wxMaUserInfo.getNickName());
                sysUser.setCity(wxMaUserInfo.getCity());
                sysUser.setProvince(wxMaUserInfo.getProvince());
                sysUser.setCountry(wxMaUserInfo.getCountry());
                sysUser.setLanguage(wxMaUserInfo.getLanguage());
                sysUser.setGender(Integer.valueOf(wxMaUserInfo.getGender()));
                sysUser.setOpenid(wxMaUserInfo.getOpenId());
                sysUser.setAppId(busiApp.getAppId());
                sysUserMapper.insertSelective(sysUser);
            }
            Map mapJWT = new HashMap();
            mapJWT.put("openid", wxMaUserInfo.getOpenId());
            mapJWT.put("avatar", sysUser.getAvatarurl());
            mapJWT.put("uid", sysUser.getId());
            String jToken = jwtUtils.creatKey(mapJWT);
            return R.renderSuccess("token", jToken);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return R.error();
    }

    @GetMapping("/getByAppId")
    public R getById(String appId) {
        if (StringUtils.isNotBlank(appId)) {
            BusiApp busiApp = new BusiApp();
            busiApp.setAppId(appId);
            busiApp = busiAppMapper.selectOne(busiApp);
            if (null != busiApp) {
                if (null != busiApp.getPageId()) {
                    BusiAppPage busiAppPage = busiAppPageMapper.selectByPrimaryKey(busiApp.getPageId());
                    if (null != busiAppPage) {
                        return R.renderSuccess("data", busiAppPage);
                    }
                }
            }

        }
        return R.error();
    }

    @GetMapping("/getArticleList")
    public R getArticleList(String appId, String type) {
        BusiArticle busiArticle = new BusiArticle();
        busiArticle.setAppId(appId);
        busiArticle.setTag(type);
        List<BusiArticle> list = busiArticleMapper.select(busiArticle);
        return R.renderSuccess("data", list);
    }

    @GetMapping("/getArticleDetail")
    public R getArticleDetail(Integer id, String appId) {
        BusiArticle busiArticle1 = new BusiArticle();
        busiArticle1.setAppId(appId);
        busiArticle1.setId(id);
        BusiArticle busiArticle = busiArticleMapper.selectOne(busiArticle1);
        return R.renderSuccess("data", busiArticle);
    }

    @PostMapping("/sendAlert")
    public R sendAlert(@RequestBody AlertHttpBodyReq alertBodyReq) throws ShowException {
//        log.info("test---{}", JSON.toJSONString(alertBodyReq.getBacklog()));
//        WxMpKefuMessage.WxArticle article = new WxMpKefuMessage.WxArticle();
//        article.setUrl("https://mass.zhihuizhan.net//#/unemp/alert");
//        article.setTitle(alertBodyReq.getEventDefinitionTitle());
//        article.setDescription(alertBodyReq.getEventDefinitionDescription());
//        article.setPicUrl("");
//        Optional.ofNullable(stringRedisTemplate.opsForList().range(SEND_MESSAGE_OPENID, 0, 10)).orElseThrow(() -> new ShowException("没有openId")).forEach(id -> {
//            try {
//                wxOpenService.getWxOpenComponentService().getWxMpServiceByAppid(ALERT_MESSAGE_APPID).getKefuService().sendKefuMessage(
//                        WxMpKefuMessage
//                                .NEWS()
//                                .addArticle(article)
//                                .toUser(id)
//                                .build());
//            } catch (WxErrorException e) {
//                e.printStackTrace();
//                log.info("发送告警失败");
//            }
//        });
        return R.ok();
    }

    @GetMapping("/getAppInfo/{appId}")
    public R getAppInfo(@PathVariable String appId) throws ShowException {
        BusiApp busiApp = Optional.ofNullable(busiAppMapper.selectOne(BusiApp.builder().appId(appId).build())).orElseThrow(() -> new ShowException("appid 不正确"));
        return R.ok(busiApp);
    }

    @GetMapping("/sendMsg/{appId}")
    public R sendMsg(@PathVariable String appId) throws ShowException {
        BusiApp busiApp = Optional.ofNullable(busiAppMapper.selectOne(BusiApp.builder().appId(appId).build())).orElseThrow(() -> new ShowException("appid 不正确"));
        wechatService.pushArticle(busiApp);
        return R.ok();
    }

}





