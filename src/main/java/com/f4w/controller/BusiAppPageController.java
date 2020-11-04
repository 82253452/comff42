package com.f4w.controller;


import com.f4w.annotation.CurrentUser;
import com.f4w.annotation.NotTokenIntecerpt;
import com.f4w.entity.BusiAppPage;
import com.f4w.entity.SysUser;
import com.f4w.mapper.BusiAppPageMapper;
import com.f4w.utils.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.MapUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/busiAppPage")
public class BusiAppPageController {
    @Resource
    public BusiAppPageMapper busiAppPageMapper;

    @GetMapping("/selectByPage")
    public R selectByPage(@CurrentUser SysUser sysUser, @RequestParam Map map) {
        BusiAppPage busiAppPage = new BusiAppPage();
        busiAppPage.setUid(sysUser.getId());
        PageHelper.startPage(MapUtils.getIntValue(map, "page", 1), MapUtils.getIntValue(map, "rows", 10));
        PageInfo<BusiAppPage> page = new PageInfo<>(busiAppPageMapper.select(busiAppPage));
        return R.ok().put("data", page);
    }

    @PostMapping("/insert")
    public R insert(@CurrentUser SysUser sysUser, @RequestBody BusiAppPage BusiAppPage) {
        BusiAppPage.setUid(sysUser.getId());
        Integer r = busiAppPageMapper.insertSelective(BusiAppPage);
        return R.ok().put("data", r);
    }

    @GetMapping("/selectById")
    public R selectById(String id) {
        BusiAppPage r = busiAppPageMapper.selectByPrimaryKey(id);
        return R.ok().put("data", r);
    }

    @PostMapping("/updateById")
    public R updateById(@RequestBody BusiAppPage BusiAppPage) {
        int r = busiAppPageMapper.updateByPrimaryKeySelective(BusiAppPage);
        return R.ok().put("data", r);
    }

    @PostMapping("/deleteByIds")
    public R deleteByIds(@RequestBody String ids) {
        int r = busiAppPageMapper.deleteByIds(ids);
        return R.ok().put("data", r);
    }

    @PostMapping("/deleteById")
    public R deleteById(@RequestBody Map param) {
        int r = busiAppPageMapper.deleteByPrimaryKey(MapUtils.getInteger(param, "id"));
        return R.ok().put("data", r);
    }
}





