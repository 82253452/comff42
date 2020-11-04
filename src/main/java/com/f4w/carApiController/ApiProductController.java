package com.f4w.carApiController;

import com.f4w.dto.req.CommonPageReq;
import com.f4w.entity.Product;
import com.f4w.mapper.ProductMapper;
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
@RequestMapping("/api/product")
public class ApiProductController {
    @Resource
    private ProductMapper mapper;

    @GetMapping("/list")
    public PageInfo<Product> list(CommonPageReq req) {
        PageInfo<Product> page = PageInfo.of(mapper.getList(req));
        return page;
    }
    @GetMapping("/detail")
    public Product detail(String id) {
        Product product = mapper.selectByPrimaryKey(id);
        return product;
    }
}
