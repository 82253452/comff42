package com.f4w.carApiController;

import com.f4w.annotation.CurrentUser;
import com.f4w.dto.SysRoleDto;
import com.f4w.dto.SysUserDto;
import com.f4w.dto.req.LoginReq;
import com.f4w.dto.req.MiniAppLoginReq;
import com.f4w.dto.resp.UserResp;
import com.f4w.entity.SysUser;
import com.f4w.mapper.SysRoleMapper;
import com.f4w.mapper.SysUserMapper;
import com.f4w.service.WechatService;
import com.f4w.utils.ForeseenException;
import com.f4w.utils.JWTUtils;
import com.f4w.utils.ShowException;
import com.f4w.utils.SystemErrorEnum;
import com.f4w.weapp.WxOpenService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.bean.WxOpenMaCodeTemplate;
import me.chanjar.weixin.open.bean.ma.WxMaOpenCommitExtInfo;
import me.chanjar.weixin.open.bean.result.WxOpenResult;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author: yp
 * @Date: 2020/9/8 10:57
 */
@RestController
@RequestMapping("/api/mini")
public class ApiMiniAppController {
    @Resource
    private WechatService wechatService;
    @Resource
    private WxOpenService wxOpenService;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private JWTUtils jwtUtils;


    @PostMapping("/login")
    public SysUserDto login(@RequestBody @Valid MiniAppLoginReq request) throws ForeseenException {
        SysUserDto sysUser = wechatService.loginFromOpenThird(request);
        return sysUser;
    }

    @GetMapping("/user")
    public SysUserDto user(MiniAppLoginReq request) throws ForeseenException {
        SysUserDto sysUser = wechatService.getUserInfoByCode(request);
        return sysUser;
    }

    @PostMapping("/getPhoneNoInfo")
    private String getPhoneNoInfo(@RequestBody @Valid MiniAppLoginReq request) throws ForeseenException {
        String phone = wechatService.getPhoneNoInfo(request);
        return phone;
    }

}
