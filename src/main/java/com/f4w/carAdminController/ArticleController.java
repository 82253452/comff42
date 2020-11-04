package com.f4w.carAdminController;

import com.f4w.dto.req.CommonPageReq;
import com.f4w.entity.Wxmp;
import com.f4w.entity.Wxmp;
import com.f4w.entity.news.Article;
import com.f4w.mapper.WxmpMapper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: yp
 * @Date: 2020/9/8 10:57
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    private WxmpMapper mapper;

    @GetMapping("/list")
    public PageInfo<Wxmp> list(CommonPageReq req) {
        PageInfo<Wxmp> page = PageInfo.of(mapper.getList(req));
        return page;
    }

    @GetMapping("/detail")
    public Wxmp detail(String id) {
        return mapper.selectByPrimaryKey(id);
    }

    @PostMapping
    public int add(@RequestBody Wxmp entity) {
        return mapper.insertSelective(entity);
    }

    @PutMapping
    public int update(@RequestBody Wxmp entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        mapper.deleteByPrimaryKey(id);
    }


}
