package com.f4w.controller;


import com.f4w.annotation.NotTokenIntecerpt;
import com.f4w.entity.SysRoleMenu;
import com.f4w.mapper.SysRoleMenuMapper;
import com.f4w.utils.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.MapUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/sysRoleMenu")
public class SysRoleMenuController {
    @Resource
    public SysRoleMenuMapper sysRoleMenuMapper;

    @GetMapping("/selectByPage")
    public R selectByPage(@RequestParam Map map) {
        PageHelper.startPage(MapUtils.getIntValue(map, "page", 1), MapUtils.getIntValue(map, "rows", 10));
        PageInfo<SysRoleMenu> page = new PageInfo<>(sysRoleMenuMapper.selectAll());
        return R.ok().put("data", page);
    }

    @PostMapping("/insert")
    public R insert(@RequestBody SysRoleMenu SysRoleMenu) {
        int r = sysRoleMenuMapper.insert(SysRoleMenu);
        return R.ok().put("data", r);
    }

    @GetMapping("/selectById")
    public R selectById(String id) {
        SysRoleMenu r = sysRoleMenuMapper.selectByPrimaryKey(id);
        return R.ok().put("data", r);
    }

    @PostMapping("/updateById")
    public R updateById(@RequestBody SysRoleMenu SysRoleMenu) {
        int r = sysRoleMenuMapper.updateByPrimaryKeySelective(SysRoleMenu);
        return R.ok().put("data", r);
    }

    @PostMapping("/deleteByIds")
    public R deleteByIds(@RequestBody String ids) {
        int r = sysRoleMenuMapper.deleteByIds(ids);
        return R.ok().put("data", r);
    }

    @PostMapping("/deleteById")
    public R deleteById(@RequestBody Map param) {
        int r = sysRoleMenuMapper.deleteByPrimaryKey(MapUtils.getInteger(param, "id"));
        return R.ok().put("data", r);
    }
}





