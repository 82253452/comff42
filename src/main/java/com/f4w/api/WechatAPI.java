package com.f4w.api;


import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.f4w.entity.BusiApp;
import com.f4w.entity.BusiAppPage;
import com.f4w.entity.BusiArticle;
import com.f4w.entity.SysUser;
import com.f4w.mapper.BusiAppMapper;
import com.f4w.mapper.BusiAppPageMapper;
import com.f4w.mapper.BusiArticleMapper;
import com.f4w.mapper.SysUserMapper;
import com.f4w.utils.JWTUtils;
import com.f4w.utils.R;
import com.f4w.utils.WxAppUtils;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/we")
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
            WxMaUserService wxMaUserService = WxAppUtils.getWxMaUserService(busiApp);
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
    public R getArticleList(String appId,String type) {
        BusiArticle busiArticle = new BusiArticle();
        busiArticle.setAppId(appId);
        busiArticle.setTag(type);
        List<BusiArticle> list = busiArticleMapper.select(busiArticle);
        return R.renderSuccess("data", list);
    }

    @GetMapping("/getArticleDetail")
    public R getArticleDetail(Long id,String appId) {
        BusiArticle busiArticle1 = new BusiArticle();
        busiArticle1.setAppId(appId);
        busiArticle1.setId(id);
        BusiArticle busiArticle = busiArticleMapper.selectOne(busiArticle1);
        return R.renderSuccess("data", busiArticle);
    }


}





