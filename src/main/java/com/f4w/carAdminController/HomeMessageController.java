package com.f4w.carAdminController;

import com.f4w.annotation.CurrentUser;
import com.f4w.dto.TransCompanyDto;
import com.f4w.dto.req.CommonPageReq;
import com.f4w.dto.req.HomeMessagePageReq;
import com.f4w.entity.HomeMessage;
import com.f4w.entity.SysUser;
import com.f4w.entity.TransCompany;
import com.f4w.mapper.HomeMessageMapper;
import com.f4w.mapper.TransCompanyMapper;
import com.f4w.service.HomeMessageService;
import com.f4w.service.TransCompanyService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import javax.annotation.Resource;

/**
 * @Author: yp
 * @Date: 2020/9/8 10:57
 */
@RestController
@RequestMapping("/homeMessage")
public class HomeMessageController {
    @Resource
    private HomeMessageMapper mapper;
    @Resource
    private HomeMessageService homeMessageService;

    @GetMapping("/list")
    public PageInfo<HomeMessage> list(HomeMessagePageReq req) {
        PageInfo<HomeMessage> page = PageInfo.of(mapper.getList(req));
        return page;
    }

    @GetMapping("/detail")
    public HomeMessage detail(String id) {
    	HomeMessage homeMessage = mapper.selectByPrimaryKey(id);
        return homeMessage;
    }

    @PostMapping
    public void add(@RequestBody HomeMessage homeMessage) {
    	homeMessageService.addMessage(homeMessage);
    }

    @PutMapping
    public void update(@RequestBody HomeMessage homeMessage) {
        mapper.updateByPrimaryKeySelective(homeMessage);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
    	homeMessageService.deleteMessage(id);
    }

}
