package com.f4w.controller;


import com.f4w.annotation.CurrentUser;
import com.f4w.annotation.NotTokenIntecerpt;
import com.f4w.entity.BusiApp;
import com.f4w.entity.BusiArticle;
import com.f4w.entity.SysUser;
import com.f4w.mapper.BusiAppMapper;
import com.f4w.mapper.BusiArticleMapper;
import com.f4w.utils.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.MapUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author admin
 */
@RestController
@RequestMapping("/busiArticle")
public class BusiArticleController {
    @Resource
    private BusiArticleMapper busiArticleMapper;
    @Resource
    private BusiAppMapper busiAppMapper;

    @GetMapping("/selectByPage")
    public R selectByPage(@CurrentUser SysUser sysUser, @RequestParam Map map) {
        BusiArticle busiArticle = new BusiArticle();
        busiArticle.setUid(sysUser.getId());
        PageHelper.startPage(MapUtils.getIntValue(map, "page", 1), MapUtils.getIntValue(map, "rows", 10));
        PageInfo<BusiArticle> page = new PageInfo<>(busiArticleMapper.select(busiArticle));
        return R.ok().put("data", page);
    }

    @GetMapping("/getApps")
    public R getApps(@CurrentUser SysUser sysUser, @RequestParam Integer type) {
        BusiApp busiApp = new BusiApp();
        busiApp.setUid(sysUser.getId());
        if (type != null) {
            busiApp.setMiniProgramInfo(type);
        }
        List<BusiApp> list = busiAppMapper.select(busiApp);
        Map map = new HashMap();
        list.forEach(e -> {
            map.put(e.getAppId(), e.getNickName());
        });
        return R.ok().put("data", map);
    }


    @PostMapping("/insert")
    public R insert(@CurrentUser SysUser sysUser, @RequestBody BusiArticle busiArticle) {
        busiArticle.setUid(sysUser.getId());
        int r = busiArticleMapper.insert(busiArticle);
        return R.ok().put("data", r);
    }

    @GetMapping("/selectById")
    public R selectById(String id) {
        BusiArticle r = busiArticleMapper.selectByPrimaryKey(id);
        return R.ok().put("data", r);
    }

    @PostMapping("/updateById")
    public R updateById(@RequestBody BusiArticle BusiArticle) {
        int r = busiArticleMapper.updateByPrimaryKeySelective(BusiArticle);
        return R.ok().put("data", r);
    }

    @PostMapping("/deleteByIds")
    public R deleteByIds(@RequestBody String ids) {
        int r = busiArticleMapper.deleteByIds(ids);
        return R.ok().put("data", r);
    }

    @PostMapping("/deleteById")
    public R deleteById(@RequestBody Map param) {
        int r = busiArticleMapper.deleteByPrimaryKey(MapUtils.getInteger(param, "id"));
        return R.ok().put("data", r);
    }
}





