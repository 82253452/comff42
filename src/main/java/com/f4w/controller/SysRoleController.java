package com.f4w.controller;


import com.f4w.annotation.NotTokenIntecerpt;
import com.f4w.entity.SysRole;
import com.f4w.mapper.SysRoleMapper;
import com.f4w.utils.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.MapUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/sysRole")
public class SysRoleController {
    @Resource
    public SysRoleMapper sysRoleMapper;

    @GetMapping("/selectByPage")
    public R selectByPage(@RequestParam Map map) {
        PageHelper.startPage(MapUtils.getIntValue(map, "page", 1), MapUtils.getIntValue(map, "rows", 10));
        PageInfo<SysRole> page = new PageInfo<>(sysRoleMapper.selectAll());
        return R.ok().put("data", page);
    }

    @PostMapping("/insert")
    public R insert(@RequestBody SysRole SysRole) {
        int r = sysRoleMapper.insert(SysRole);
        return R.ok().put("data", r);
    }

    @GetMapping("/selectById")
    public R selectById(String id) {
        SysRole r = sysRoleMapper.selectByPrimaryKey(id);
        return R.ok().put("data", r);
    }

    @PostMapping("/updateById")
    public R updateById(@RequestBody SysRole SysRole) {
        int r = sysRoleMapper.updateByPrimaryKeySelective(SysRole);
        return R.ok().put("data", r);
    }

    @PostMapping("/deleteByIds")
    public R deleteByIds(@RequestBody String ids) {
        int r = sysRoleMapper.deleteByIds(ids);
        return R.ok().put("data", r);
    }

    @PostMapping("/deleteById")
    public R deleteById(@RequestBody Map param) {
        int r = sysRoleMapper.deleteByPrimaryKey(MapUtils.getInteger(param, "id"));
        return R.ok().put("data", r);
    }
}





