package com.f4w.carAdminController;

import com.f4w.dto.req.CommonPageReq;
import com.f4w.entity.Banner;
import com.f4w.mapper.BannerMapper;
import com.f4w.utils.ForeseenException;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: yp
 * @Date: 2020/9/8 10:57
 */
@RestController
@RequestMapping("/banner")
public class BannerController {
    @Resource
    private BannerMapper bannerMapper;

    @GetMapping("/list")
    public PageInfo<Banner> list(CommonPageReq req) throws ForeseenException {
        PageInfo<Banner> page = PageInfo.of(bannerMapper.getList(req));
        return page;
    }

    @PostMapping
    public int add(@RequestBody Banner banner) throws ForeseenException {
        int i = bannerMapper.insertSelective(banner);
        return i;
    }

    @PutMapping
    public int update(@RequestBody Banner banner) throws ForeseenException {
        int i = bannerMapper.updateByPrimaryKeySelective(banner);
        return i;
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable String id) throws ForeseenException {
        int i = bannerMapper.deleteByPrimaryKey(id);
        return i;
    }

}
