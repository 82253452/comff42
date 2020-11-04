package com.f4w.carAdminController;

import com.f4w.dto.req.CommonPageReq;
import com.f4w.entity.BusiApp;
import com.f4w.mapper.BusiAppMapper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: yp
 * @Date: 2020/9/8 10:57
 */
@RestController
@RequestMapping("/openApp")
public class OpenAppController {
    @Resource
    private BusiAppMapper mapper;

    @GetMapping("/list")
    public PageInfo<BusiApp> list(CommonPageReq req) {
        PageInfo<BusiApp> page = PageInfo.of(mapper.getList(req));
        return page;
    }

    @GetMapping("/detail")
    public BusiApp detail(String id) {
        return mapper.selectByPrimaryKey(id);
    }

    @PostMapping
    public int add(@RequestBody BusiApp entity) {
        return mapper.insertSelective(entity);
    }

    @PutMapping
    public int update(@RequestBody BusiApp entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        mapper.deleteByPrimaryKey(id);
    }


}
