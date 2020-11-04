package com.f4w.carAdminController;

import com.f4w.dto.req.CommonPageReq;
import com.f4w.entity.Product;
import com.f4w.mapper.ProductMapper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: yp
 * @Date: 2020/9/8 10:57
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Resource
    private ProductMapper mapper;

    @GetMapping("/list")
    public PageInfo<Product> list(CommonPageReq req) {
        PageInfo<Product> page = PageInfo.of(mapper.getList(req));
        return page;
    }

    @GetMapping("/detail")
    public Product detail(String id) {
        return mapper.selectByPrimaryKey(id);
    }

    @PostMapping
    public int add(@RequestBody Product entity) {
        return mapper.insertSelective(entity);
    }

    @PutMapping
    public int update(@RequestBody Product entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        mapper.deleteByPrimaryKey(id);
    }


}
