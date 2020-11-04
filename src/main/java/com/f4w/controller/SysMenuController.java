package com.f4w.controller;


import com.f4w.annotation.NotTokenIntecerpt;
import com.f4w.entity.SysMenu;
import com.f4w.mapper.SysMenuMapper;
import com.f4w.utils.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.MapUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/sysMenu")
public class SysMenuController {
    @Resource
    public SysMenuMapper sysMenuMapper;

    @GetMapping("/selectByPage")
    public R selectByPage(@RequestParam Map map) {
        PageHelper.startPage(MapUtils.getIntValue(map, "page", 1), MapUtils.getIntValue(map, "rows", 10));
        PageInfo<SysMenu> page =new PageInfo<>(sysMenuMapper.selectAll());
        return R.ok().put("data", page);
    }

    @PostMapping("/insert")
    public R insert(@RequestBody SysMenu SysMenu) {
        int r =  sysMenuMapper.insert(SysMenu);
        return R.ok().put("data", r);
    }

    @GetMapping("/selectById")
    public R selectById(String id) {
        SysMenu r =  sysMenuMapper.selectByPrimaryKey(id);
        return R.ok().put("data", r);
    }

    @PostMapping("/updateById")
    public R updateById(@RequestBody SysMenu SysMenu) {
        int r =  sysMenuMapper.updateByPrimaryKeySelective(SysMenu);
        return R.ok().put("data", r);
    }

    @PostMapping("/deleteByIds")
    public R deleteByIds(@RequestBody String ids) {
        int r =  sysMenuMapper.deleteByIds(ids);
        return R.ok().put("data", r);
    }

    @PostMapping("/deleteById")
    public R deleteById(@RequestBody Map param) {
        int r =  sysMenuMapper.deleteByPrimaryKey(MapUtils.getInteger(param, "id"));
        return R.ok().put("data", r);
    }
}





