package com.f4w.controller;


import com.alibaba.excel.EasyExcel;
import com.f4w.dto.req.TalentPoolReq;
import com.f4w.entity.TalentPool;
import com.f4w.mapper.TalentPoolMapper;
import com.f4w.utils.R;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/talent")
public class TalentPoolController {
    @Resource
    public TalentPoolMapper talentPoolMapper;

    @GetMapping("/page")
    public R selectByPage(TalentPoolReq req) {
        PageInfo<TalentPool> page = PageInfo.of(talentPoolMapper.selectAllByPage(req));
        return R.ok(page);
    }

    @GetMapping("/selectScs")
    public R selectScs() {
        List<String> strings = talentPoolMapper.selectScs();
        return R.ok(strings);
    }

    @GetMapping("/selectEdus")
    public R selectEdus() {
        List<String> strings = talentPoolMapper.selectEdus();
        return R.ok(strings);
    }

    @GetMapping("/selectMajors")
    public R selectMajors() {
        List<String> strings = talentPoolMapper.selectMajors();
        return R.ok(strings);
    }

    @PutMapping
    public R updateById(@RequestBody TalentPool talentPool) {
        int r =  talentPoolMapper.updateByPrimaryKeySelective(talentPool);
        return R.ok(r);
    }

    @Delete("/deleteByIds")
    public R deleteByIds(@RequestBody String ids) {
        int r =  talentPoolMapper.deleteByIds(ids);
        return R.ok(r);
    }

    @Delete("/{id}")
    public R deleteById(@PathVariable Integer id) {
        int r = talentPoolMapper.deleteByPrimaryKey(id);
        return R.ok(r);
    }

    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response, TalentPoolReq reportRequest) {
        List<TalentPool> list = talentPoolMapper.selectAllByPage(null);
        System.out.println(list.size());
        try{
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("渠道报表", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), TalentPool.class).sheet("模板").doWrite(list);
        }catch (Exception e){
            System.out.println(e);
        }

    }
}





