package com.f4w.controller;

import com.f4w.dto.req.CommonPageReq;
import com.f4w.entity.Banner;
import com.f4w.entity.SysUser;
import com.f4w.mapper.BannerMapper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: yp
 * @Date: 2020/9/8 10:57
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    private BannerMapper bannerMapper;

    @GetMapping("/test")
    public PageInfo<Banner> list(CommonPageReq req, SysUser sysUser) {
        PageInfo<Banner> page = PageInfo.of(bannerMapper.getList(req));
        return page;
    }


}
