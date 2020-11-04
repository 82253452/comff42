package com.f4w.service;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.f4w.dto.SysUserDto;
import com.f4w.dto.TransCompanyUserDto;
import com.f4w.dto.req.MiniAppLoginReq;
import com.f4w.entity.*;
import com.f4w.job.WechatPushArticleJob;
import com.f4w.mapper.CompanyMapper;
import com.f4w.mapper.DriverMapper;
import com.f4w.mapper.SysUserMapper;
import com.f4w.mapper.TransCompanyUserMapper;
import com.f4w.utils.Constant;
import com.f4w.utils.ForeseenException;
import com.f4w.utils.JWTUtils;
import com.f4w.utils.ShowException;
import com.f4w.weapp.WxOpenService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class WechatService {
    @Resource
    private WechatPushArticleJob wechatPushArticleJob;
    @Resource
    private WxOpenService wxOpenService;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private TransCompanyUserMapper transCompanyUserMapper;
    @Resource
    private CompanyMapper companyMapper;
    @Resource
    private JWTUtils jwtUtils;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private DriverMapper driverMapper;
    @Async
    public void pushArticle(BusiApp busiApp) {
        wechatPushArticleJob.execute(busiApp.getMessageParam());
    }

    public SysUserDto loginFromOpenThird(MiniAppLoginReq miniAppLoginDto) throws ForeseenException {
        try {
            WxMaJscode2SessionResult session = wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(miniAppLoginDto.getAppId()).getUserService().getSessionInfo(miniAppLoginDto.getCode());
            WxMaUserInfo userInfo = wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(miniAppLoginDto.getAppId()).getUserService().getUserInfo(session.getSessionKey(), miniAppLoginDto.getEncryptedData(), miniAppLoginDto.getIv());
            SysUser sysUser = sysUserMapper.selectOne(SysUser.builder().unionid(userInfo.getUnionId()).build());
            if (sysUser == null) {
                sysUser = SysUser.builder()
                        .openid(userInfo.getOpenId())
                        .nickname(userInfo.getNickName())
                        .gender(Integer.valueOf(userInfo.getGender()))
                        .language(userInfo.getLanguage())
                        .city(userInfo.getCity())
                        .province(userInfo.getProvince())
                        .country(userInfo.getCountry())
                        .avatarurl(userInfo.getAvatarUrl())
                        .unionid(userInfo.getUnionId())
                        .appId(miniAppLoginDto.getAppId())
                        .userName(userInfo.getNickName())
                        .build();
                sysUserMapper.insertSelective(sysUser);
            }
            stringRedisTemplate.opsForHash().put(String.format(Constant.USER_OPEN_ID,miniAppLoginDto.getAppId()),sysUser.getId().toString(),session.getOpenid());
            SysUserDto sysUserDto = setUserInfo(sysUser);
            return sysUserDto;
        } catch (Exception e) {
            System.out.println(e);
            log.error("用户授权失败", e);
            throw new ForeseenException(e.getMessage());
        }
        // 解密用户信息
    }

    public SysUserDto getUserInfoByCode(MiniAppLoginReq request) throws ForeseenException {
        try {
            WxMaJscode2SessionResult session = wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(request.getAppId()).getUserService().getSessionInfo(request.getCode());
            //如果没有unionid 就用openid 去登录
            SysUser sysUser;
            if (StringUtils.isNotBlank(session.getUnionid())) {
                sysUser = sysUserMapper.selectOne(SysUser.builder().unionid(session.getUnionid()).build());
            } else {
                sysUser = sysUserMapper.selectOne(SysUser.builder().openid(session.getOpenid()).build());
            }
            if (sysUser == null) {
                return null;
            }
            stringRedisTemplate.opsForHash().put(String.format(Constant.USER_OPEN_ID,request.getAppId()),sysUser.getId().toString(),session.getOpenid());
            SysUserDto sysUserDto = setUserInfo(sysUser);
            return sysUserDto;
        } catch (Exception e) {
            log.error("用户授权失败", e);
            throw new ForeseenException(e.getMessage());
        }
    }

    private SysUserDto setUserInfo(SysUser sysUser) {
        SysUserDto sysUserDto = SysUserDto.builder().build();
        BeanUtils.copyProperties(sysUser, sysUserDto);
        sysUserDto.setToken(jwtUtils.creatKey(sysUser));
        //所属物流公司
        List<TransCompany> userCompanyList = transCompanyUserMapper.getUserCompanyList(sysUser.getId());
        //所属企业信息
        Company company = companyMapper.selectOne(Company.builder().userId(sysUser.getId()).build());
        //司机认证信息
        Driver driver = driverMapper.selectOne(Driver.builder().userId(sysUser.getId()).build());
        sysUserDto.setTransCompanyList(userCompanyList);
        sysUserDto.setCompany(company);
        sysUserDto.setDriver(driver);
        return sysUserDto;
    }

    public String getPhoneNoInfo(MiniAppLoginReq request) throws ForeseenException {
        WxMaJscode2SessionResult session;
        try {
            session = wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(request.getAppId()).getUserService().getSessionInfo(request.getCode());
        } catch (WxErrorException e) {
            log.error("用户授权失败", e);
            throw new ForeseenException(e.getMessage());
        }
        if (session == null) {
            throw new ShowException("获取手机号失败");
        }
        String sessionKey = session.getSessionKey();
        WxMaPhoneNumberInfo phoneNoInfo = wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(request.getAppId()).getUserService().getPhoneNoInfo(sessionKey, request.getEncryptedData(), request.getIv());
        if (phoneNoInfo == null) {
            throw new ShowException("获取手机号失败");
        }
        SysUser sysUser;
        if (StringUtils.isNotBlank(session.getUnionid())) {
            sysUser = sysUserMapper.selectOne(SysUser.builder().unionid(session.getUnionid()).build());
        } else {
            sysUser = sysUserMapper.selectOne(SysUser.builder().openid(session.getOpenid()).build());
        }
        sysUser.setPhone(phoneNoInfo.getPhoneNumber());
        sysUserMapper.updateByPrimaryKeySelective(sysUser);
        return phoneNoInfo.getPhoneNumber();
    }
}
