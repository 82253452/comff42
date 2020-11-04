package com.f4w.carApiController;

import com.f4w.dto.req.CarTypeReq;
import com.f4w.entity.CarType;
import com.f4w.mapper.CarTypeMapper;
import com.f4w.utils.ForeseenException;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: yp
 * @Date: 2020/9/8 10:57
 */
@RestController
@RequestMapping("/api/car")
public class ApiCarTypeController {
    @Resource
    private CarTypeMapper carTypeMapper;

    @GetMapping("/list")
    public PageInfo<CarType> list(CarTypeReq req) throws ForeseenException {
        PageInfo<CarType> page = PageInfo.of(carTypeMapper.getList(req));
        return page;
    }

    @PostMapping
    public int add(@RequestBody CarType req) throws ForeseenException {
        int i = carTypeMapper.insertSelective(req);
        return i;
    }

    @PutMapping
    public int update(@RequestBody CarType req) throws ForeseenException {
        int i = carTypeMapper.updateByPrimaryKeySelective(req);
        return i;
    }

    @DeleteMapping("{id}")
    public int delete(@PathVariable String id) throws ForeseenException {
        int i = carTypeMapper.deleteByPrimaryKey(id);
        return i;
    }

}
