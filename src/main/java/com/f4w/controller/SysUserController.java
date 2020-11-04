package com.f4w.controller;


import com.f4w.annotation.NotTokenIntecerpt;
import com.f4w.dto.req.UserReq;
import com.f4w.entity.SysUser;
import com.f4w.entity.SysUserRole;
import com.f4w.mapper.SysUserMapper;
import com.f4w.mapper.SysUserRoleMapper;
import com.f4w.utils.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiniu.util.Md5;
import org.apache.commons.collections4.MapUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/sysUser")
public class SysUserController {
    @Resource
    public SysUserMapper sysUserMapper;
    @Resource
    public SysUserRoleMapper sysUserRoleMapper;

    @GetMapping("/selectByPage")
    public R selectByPage(@RequestParam Map map) {
        PageHelper.startPage(MapUtils.getIntValue(map, "page", 1), MapUtils.getIntValue(map, "limit", 10));
        PageInfo<SysUser> page = new PageInfo<>(sysUserMapper.selectAll());
        return R.ok().put("data", page);
    }

    @PostMapping("/addUser")
    public R addUser(@RequestBody UserReq userReq) {
        SysUser sysUser = SysUser.builder()
                .userName(userReq.getUserName())
                .nickname(userReq.getNickname())
                .password(Md5.md5(userReq.getPassword().getBytes()))
                .gender(userReq.getGender())
                .build();
        sysUserMapper.insert(sysUser);
        sysUserRoleMapper.delete(SysUserRole.builder().userId(sysUser.getId()).build());
        sysUserRoleMapper.insert(SysUserRole.builder().userId(sysUser.getId()).roleId(userReq.getRoleId()).build());
        return R.ok();
    }

    @PostMapping("/resetPassword")
    public R resetPassword(@RequestBody UserReq userReq) {
        sysUserMapper.updateByPrimaryKeySelective(SysUser.builder()
                .id(userReq.getId())
                .password(Md5.md5(userReq.getPassword().getBytes()))
                .build());
        return R.ok();
    }

    @PostMapping("/insert")
    public R insert(@RequestBody SysUser SysUser) {
        int r = sysUserMapper.insert(SysUser);
        return R.ok().put("data", r);
    }

    @GetMapping("/selectById")
    public R selectById(String id) {
        SysUser r = sysUserMapper.selectByPrimaryKey(id);
        return R.ok().put("data", r);
    }

    @PostMapping("/updateById")
    public R updateById(@RequestBody SysUser SysUser) {
        int r = sysUserMapper.updateByPrimaryKeySelective(SysUser);
        return R.ok().put("data", r);
    }

    @PostMapping("/deleteByIds")
    public R deleteByIds(@RequestBody String ids) {
        int r = sysUserMapper.deleteByIds(ids);
        return R.ok().put("data", r);
    }

    @PostMapping("/deleteById")
    public R deleteById(@RequestBody Map param) {
        int r = sysUserMapper.deleteByPrimaryKey(MapUtils.getInteger(param, "id"));
        return R.ok().put("data", r);
    }
}





