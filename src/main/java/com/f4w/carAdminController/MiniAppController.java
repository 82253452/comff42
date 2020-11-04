package com.f4w.carAdminController;

import com.f4w.annotation.CurrentUser;
import com.f4w.annotation.NotTokenIntecerpt;
import com.f4w.dto.SysRoleDto;
import com.f4w.dto.req.CommonPageReq;
import com.f4w.dto.req.LoginReq;
import com.f4w.dto.req.MiniAppLoginReq;
import com.f4w.dto.resp.UserResp;
import com.f4w.entity.BusiApp;
import com.f4w.entity.BusiAppPage;
import com.f4w.entity.SysUser;
import com.f4w.mapper.BusiAppMapper;
import com.f4w.mapper.BusiAppPageMapper;
import com.f4w.mapper.SysRoleMapper;
import com.f4w.mapper.SysUserMapper;
import com.f4w.service.WechatService;
import com.f4w.utils.*;
import com.f4w.weapp.WxOpenService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.bean.WxOpenMaCodeTemplate;
import me.chanjar.weixin.open.bean.ma.WxMaOpenCommitExtInfo;
import me.chanjar.weixin.open.bean.result.WxOpenResult;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

/**
 * @Author: yp
 * @Date: 2020/9/8 10:57
 */
@RestController
@RequestMapping("/mini")
public class MiniAppController {
    @Resource
    private WechatService wechatService;
    @Resource
    private WxOpenService wxOpenService;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private BusiAppMapper busiAppMapper;
    @Resource
    private JWTUtils jwtUtils;

    @PostMapping("/userLogin")
    public UserResp login(@Validated @RequestBody LoginReq req) throws ShowException {
        SysUser sysUser = Optional.ofNullable(sysUserMapper.selectOne(SysUser.builder().userName(req.getUsername()).build())).orElseThrow(() -> new ShowException(SystemErrorEnum.USER_ERROR));
        if (!StringUtils.equals(DigestUtils.md5Hex(req.getPassword()), sysUser.getPassword())) {
            throw new ShowException(SystemErrorEnum.USER_ERROR);
        }
        return UserResp.builder().uid(sysUser.getId().intValue()).userName(sysUser.getUserName()).token(jwtUtils.creatKey(sysUser)).build();
    }

    /**
     * 获取用户信息
     *
     * @param sysUser
     * @return
     */
    @GetMapping("/getUserInfo")
    public UserResp adminGetUserInfo(@CurrentUser SysUser sysUser) {
        List<SysRoleDto> roleDtos = sysRoleMapper.getRolesByUserId(sysUser.getId());
        ArrayList<String> roles = new ArrayList();
        roleDtos.forEach(e -> roles.add(e.getRoleName()));
        return UserResp.builder().userInfo(sysUser).roles(roles).build();
    }

    @GetMapping("/getAppPages")
    public PageInfo<BusiApp> getAppPages(@CurrentUser SysUser sysUser, CommonPageReq req) {
        PageInfo<BusiApp> of = PageInfo.of(busiAppMapper.select(BusiApp.builder().miniProgramInfo(req.getType()).build()));
        return of;
    }

    /**
     * 获取草稿箱内的所有临时代码草稿
     *
     * @return
     * @throws WxErrorException
     */
    @GetMapping("/getTrafts")
    public List<WxOpenMaCodeTemplate> getTrafts() throws WxErrorException {
        List<WxOpenMaCodeTemplate> templateDraftList = wxOpenService.getWxOpenComponentService().getTemplateDraftList();
        return templateDraftList;
    }

    /**
     * 获取代码模版库中的所有小程序代码模版
     *
     * @return
     * @throws WxErrorException
     */
    @GetMapping("/getTemplates")
    public List<WxOpenMaCodeTemplate> getTemplates() throws WxErrorException {
        List<WxOpenMaCodeTemplate> templateList = wxOpenService.getWxOpenComponentService().getTemplateList();
        return templateList;
    }

    /**
     * 上传小程序代码
     *
     * @return
     * @throws WxErrorException
     */
    @GetMapping("/uploadApp")
    public WxOpenResult uploadApp(String appId, Long templateId) throws WxErrorException {
        WxMaOpenCommitExtInfo extInfo = WxMaOpenCommitExtInfo.INSTANCE();
        WxOpenResult wxOpenResult = wxOpenService
                .getWxOpenComponentService()
                .getWxMaServiceByAppid(appId)
                .codeCommit(templateId, "1.0", "发布", extInfo);
        return wxOpenResult;
    }
}
