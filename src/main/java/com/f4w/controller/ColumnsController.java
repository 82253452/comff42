package com.f4w.controller;


import com.f4w.annotation.NotTokenIntecerpt;
import com.f4w.dto.req.ColumnsReq;
import com.f4w.entity.Columns;
import com.f4w.mapper.ColumnsMapper;
import com.f4w.utils.R;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/columns")
public class ColumnsController {
    @Resource
    private ColumnsMapper columnsMapper;

    @GetMapping
    public R selectByPage(ColumnsReq request) {
        return R.ok(PageInfo.of(columnsMapper.selectByPage(request)));
    }

    @GetMapping("/all")
    public R all() {
        return R.ok(columnsMapper.selectAll());
    }


    @PostMapping
    public R insert(@RequestBody Columns columns) {
        columnsMapper.insert(columns);
        return R.ok();
    }

    @GetMapping("/{id}")
    public R selectById(@PathVariable String id) {
        Columns r = columnsMapper.selectByPrimaryKey(id);
        return R.ok(r);
    }

    @PutMapping
    public R updateById(@RequestBody Columns columns) {
        columnsMapper.updateByPrimaryKeySelective(columns);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R deleteById(@PathVariable String id) {
        columnsMapper.deleteByPrimaryKey(id);
        return R.ok();
    }
}





