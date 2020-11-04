package com.f4w.carAdminController;

import com.f4w.dto.req.CommonPageReq;
import com.f4w.entity.Driver;
import com.f4w.mapper.DriverMapper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: yp
 * @Date: 2020/9/8 10:57
 */
@RestController
@RequestMapping("/driver")
public class DriverController {
    @Resource
    private DriverMapper mapper;

    @GetMapping("/list")
    public PageInfo<Driver> list(CommonPageReq req) {
        PageInfo<Driver> page = PageInfo.of(mapper.getList(req));
        return page;
    }

    @GetMapping("/detail")
    public Driver detail(String id) {
        return mapper.selectByPrimaryKey(id);
    }

    @PostMapping
    public int add(@RequestBody Driver driver) {
        return mapper.insertSelective(driver);
    }

    @PutMapping
    public int update(@RequestBody Driver driver) {
        return mapper.updateByPrimaryKeySelective(driver);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        mapper.deleteByPrimaryKey(id);
    }


}
