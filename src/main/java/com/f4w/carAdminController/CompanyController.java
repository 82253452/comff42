package com.f4w.carAdminController;

import com.f4w.annotation.CurrentUser;
import com.f4w.dto.req.CommonPageReq;
import com.f4w.entity.Company;
import com.f4w.entity.SysUser;
import com.f4w.mapper.CompanyMapper;
import com.f4w.utils.ForeseenException;
import com.f4w.utils.Result;
import com.f4w.utils.ShowException;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @Author: yp
 * @Date: 2020/9/8 10:57
 */
@RestController
@RequestMapping("/company")
public class CompanyController {
    @Resource
    private CompanyMapper mapper;

    @GetMapping("/list")
    public PageInfo<Company> list(CommonPageReq req) throws ForeseenException {
        PageInfo<Company> page = PageInfo.of(mapper.getList(req));
        return page;
    }

    @GetMapping("/detail")
    public Company detail(String id) throws ForeseenException {
        Company transCompany = mapper.selectByPrimaryKey(id);
        return transCompany;
    }

    @PostMapping
    public int add(@RequestBody Company banner) throws ForeseenException {
        int i = mapper.insertSelective(banner);
        return i;
    }

    @PutMapping
    public int update(@RequestBody Company banner) throws ForeseenException {
        int i = mapper.updateByPrimaryKeySelective(banner);
        return i;
    }

    @DeleteMapping("{id}")
    public int delete(@PathVariable String id) throws ForeseenException {
        int i = mapper.deleteByPrimaryKey(id);
        return i;
    }

    @PostMapping("/certCompany")
    public void certCompany(@CurrentUser SysUser sysUser, @RequestBody Company company) throws ForeseenException {
        Company cert = mapper.selectOne(Company.builder().userId(sysUser.getId().intValue()).build());
        if (cert != null && !cert.getStatus().equals(2)) {
            throw new ShowException("您已经提交企业认证了");
        }
        if (cert != null) {
            company.setId(cert.getId());
            mapper.updateByPrimaryKeySelective(company);
        } else {
            company.setUserId(sysUser.getId());
            mapper.insertSelective(company);
        }
    }

    @GetMapping("/checkCompany")
    public void checkCompany(Integer id, Integer status) throws ForeseenException {
        Company company = Optional.ofNullable(mapper.selectByPrimaryKey(id)).orElseThrow(() -> new ShowException("id有误"));
        if (company.getStatus().equals(0) || company.getStatus().equals(2)) {
            company.setStatus(status);
            mapper.updateByPrimaryKeySelective(company);
        }
    }


}
