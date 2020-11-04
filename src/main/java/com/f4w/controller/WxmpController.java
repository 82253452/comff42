package com.f4w.controller;


import com.f4w.entity.Wxmp;
import com.f4w.mapper.WxmpMapper;
import com.f4w.utils.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.MapUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/wxmp")
public class WxmpController {
    @Resource
    public WxmpMapper wxmpMapper;

    @GetMapping("/selectByPage")
    public R selectByPage(@RequestParam Map map) {
        PageHelper.startPage(MapUtils.getIntValue(map, "page", 1), MapUtils.getIntValue(map, "limit", 10));
        PageInfo<Wxmp> page = new PageInfo<>(wxmpMapper.selectAllByPage(map));
        return R.ok().put("data", page);
    }

    @GetMapping("/selectCurrentByPage")
    public R selectCurrentByPage(@RequestParam Map map) {
        PageHelper.startPage(MapUtils.getIntValue(map, "page", 1), MapUtils.getIntValue(map, "limit", 10));
        PageInfo<Wxmp> page = new PageInfo<>(wxmpMapper.selectCurrentByPage(map));
        return R.ok().put("data", page);
    }

    @PostMapping("/insert")
    public R insert(@RequestBody Wxmp Wxmp) {
        int r = wxmpMapper.insert(Wxmp);
        return R.ok().put("data", r);
    }

    @GetMapping("/selectById")
    public R selectById(String id) {
        Wxmp r =  wxmpMapper.selectByPrimaryKey(id);
        return R.ok().put("data", r);
    }

    @PostMapping("/updateById")
    public R updateById(@RequestBody Wxmp Wxmp) {
        int r =  wxmpMapper.updateByPrimaryKeySelective(Wxmp);
        return R.ok().put("data", r);
    }

    @PostMapping("/deleteByIds")
    public R deleteByIds(@RequestBody String ids) {
        int r =  wxmpMapper.deleteByIds(ids);
        return R.ok().put("data", r);
    }

    @PostMapping("/deleteById")
    public R deleteById(@RequestBody Map param) {
        int r = wxmpMapper.deleteByPrimaryKey(MapUtils.getInteger(param, "id"));
        return R.ok().put("data", r);
    }
}





