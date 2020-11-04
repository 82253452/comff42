package com.f4w.carAdminController;

import com.f4w.dto.req.CommonPageReq;
import com.f4w.entity.Columns;
import com.f4w.mapper.ColumnsMapper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: yp
 * @Date: 2020/9/8 10:57
 */
@RestController
@RequestMapping("/column")
public class ColumnController {
    @Resource
    private ColumnsMapper mapper;

    @GetMapping("/all")
    public List<Columns> all() {
        List<Columns> columns = mapper.selectAll();
        return columns;
    }

    @GetMapping("/list")
    public PageInfo<Columns> list(CommonPageReq req) {
        PageInfo<Columns> page = PageInfo.of(mapper.getList(req));
        return page;
    }

    @GetMapping("/detail")
    public Columns detail(String id) {
        return mapper.selectByPrimaryKey(id);
    }

    @PostMapping
    public int add(@RequestBody Columns entity) {
        return mapper.insertSelective(entity);
    }

    @PutMapping
    public int update(@RequestBody Columns entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        mapper.deleteByPrimaryKey(id);
    }


}
