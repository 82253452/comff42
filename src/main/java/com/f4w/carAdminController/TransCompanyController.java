package com.f4w.carAdminController;

import com.f4w.annotation.CurrentUser;
import com.f4w.dto.TransCompanyDto;
import com.f4w.dto.req.CommonPageReq;
import com.f4w.entity.SysUser;
import com.f4w.entity.TransCompany;
import com.f4w.mapper.TransCompanyMapper;
import com.f4w.service.TransCompanyService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: yp
 * @Date: 2020/9/8 10:57
 */
@RestController
@RequestMapping("/trans")
public class TransCompanyController {
    @Resource
    private TransCompanyMapper mapper;
    @Resource
    private TransCompanyService transCompanyService;

    @GetMapping("/list")
    public PageInfo<TransCompany> list(CommonPageReq req) {
        PageInfo<TransCompany> page = PageInfo.of(mapper.getList(req));
        return page;
    }

    @GetMapping("/user/list")
    public PageInfo<TransCompanyDto> userList(@CurrentUser SysUser sysUser, CommonPageReq req) {
        req.setUserId(sysUser.getId().intValue());
        PageInfo<TransCompanyDto> page = PageInfo.of(mapper.getUserList(req));
        return page;
    }

    @GetMapping("/detail")
    public TransCompany detail(String id) {
        TransCompany transCompany = mapper.selectByPrimaryKey(id);
        return transCompany;
    }

    @PostMapping
    public void add(@RequestBody TransCompany transCompany) {
        transCompanyService.addCompany(transCompany);
    }

    @PutMapping
    public void update(@RequestBody TransCompany banner) {
        mapper.updateByPrimaryKeySelective(banner);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        transCompanyService.deleteCompany(id);
    }

}
