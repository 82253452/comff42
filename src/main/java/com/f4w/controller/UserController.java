package com.f4w.controller;

import com.f4w.annotation.CurrentUser;
import com.f4w.annotation.NotTokenIntecerpt;
import com.f4w.dto.SysMenuDto;
import com.f4w.dto.SysRoleDto;
import com.f4w.dto.resp.UserResp;
import com.f4w.entity.SysUser;
import com.f4w.mapper.SysMenuMapper;
import com.f4w.mapper.SysRoleMapper;
import com.f4w.mapper.SysUserMapper;
import com.f4w.utils.JWTUtils;
import com.f4w.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("user")
@NotTokenIntecerpt
public class UserController {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysMenuMapper sysMenuMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private JWTUtils jwtUtils;

    @GetMapping("getUserRole")
    public R getUserRole() {
        return R.renderSuccess("uptoken", "");
    }

    /**
     * 获取用户权限
     *
     * @param sysUser
     * @return
     */
    @GetMapping("getUserPermision")
    public R getUserPermision(@CurrentUser SysUser sysUser) {
        List<SysMenuDto> list = sysMenuMapper.getMenuByUserId(sysUser.getId());
        return R.renderSuccess("menus", list);
    }

    /**
     * 获取用户权限
     *
     * @param sysUser
     * @return
     */
    @GetMapping("getUserRoles")
    public R getUserRoles(@CurrentUser SysUser sysUser) {
        List<SysMenuDto> list = sysMenuMapper.getMenuByUserId(sysUser.getId());
        return R.renderSuccess("menus", list);
    }

    /**
     * 获取用户信息
     *
     * @param sysUser
     * @return
     */
    @GetMapping("getUserInfo")
    public R getUserInfo(@CurrentUser SysUser sysUser) {
        Map render = new HashMap(2);
        render.put("userInfo", sysUser);
        List<SysRoleDto> roleDtos = sysRoleMapper.getRolesByUserId(sysUser.getId());
        List<String> roles = new ArrayList();
        roleDtos.forEach(e -> {
            roles.add(e.getRoleName());
        });
        render.put("roles", roles);
        return R.renderSuccess("user", render);
    }




}
