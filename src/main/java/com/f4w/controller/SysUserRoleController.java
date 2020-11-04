package com.f4w.controller;


import com.f4w.annotation.NotTokenIntecerpt;
import com.f4w.entity.SysUserRole;
import com.f4w.mapper.SysUserRoleMapper;
import com.f4w.utils.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.MapUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/sysUserRole")
public class SysUserRoleController {
    @Resource
    public SysUserRoleMapper sysUserRoleMapper;

    @GetMapping("/selectByPage")
    public R selectByPage(@RequestParam Map map) {
        PageHelper.startPage(MapUtils.getIntValue(map, "page", 1), MapUtils.getIntValue(map, "rows", 10));
        PageInfo<SysUserRole> page = new PageInfo<>(sysUserRoleMapper.selectAll());
        return R.ok().put("data", page);
    }

    @PostMapping("/insert")
    public R insert(@RequestBody SysUserRole SysUserRole) {
        int r = sysUserRoleMapper.insert(SysUserRole);
        return R.ok().put("data", r);
    }

    @GetMapping("/selectById")
    public R selectById(String id) {
        SysUserRole r = sysUserRoleMapper.selectByPrimaryKey(id);
        return R.ok().put("data", r);
    }

    @PostMapping("/updateById")
    public R updateById(@RequestBody SysUserRole SysUserRole) {
        int r = sysUserRoleMapper.updateByPrimaryKeySelective(SysUserRole);
        return R.ok().put("data", r);
    }

    @PostMapping("/deleteByIds")
    public R deleteByIds(@RequestBody String ids) {
        int r = sysUserRoleMapper.deleteByIds(ids);
        return R.ok().put("data", r);
    }

    @PostMapping("/deleteById")
    public R deleteById(@RequestBody Map param) {
        int r = sysUserRoleMapper.deleteByPrimaryKey(MapUtils.getInteger(param, "id"));
        return R.ok().put("data", r);
    }
}





