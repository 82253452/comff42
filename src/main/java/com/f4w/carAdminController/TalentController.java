package com.f4w.carAdminController;

import com.f4w.dto.req.CommonPageReq;
import com.f4w.entity.TalentPool;
import com.f4w.entity.TalentPool;
import com.f4w.mapper.TalentPoolMapper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: yp
 * @Date: 2020/9/8 10:57
 */
@RestController
@RequestMapping("/talentPool")
public class TalentController {
    @Resource
    private TalentPoolMapper mapper;

    @GetMapping("/list")
    public PageInfo<TalentPool> list(CommonPageReq req) {
        PageInfo<TalentPool> page = PageInfo.of(mapper.getList(req));
        return page;
    }

    @GetMapping("/detail")
    public TalentPool detail(String id) {
        return mapper.selectByPrimaryKey(id);
    }

    @PostMapping
    public int add(@RequestBody TalentPool entity) {
        return mapper.insertSelective(entity);
    }

    @PutMapping
    public int update(@RequestBody TalentPool entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        mapper.deleteByPrimaryKey(id);
    }


}
