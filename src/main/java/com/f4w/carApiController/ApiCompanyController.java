package com.f4w.carApiController;

import com.f4w.annotation.CurrentUser;
import com.f4w.dto.req.CommonPageReq;
import com.f4w.entity.Company;
import com.f4w.entity.SysUser;
import com.f4w.mapper.CompanyMapper;
import com.f4w.utils.ForeseenException;
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
@RequestMapping("/api/company")
public class ApiCompanyController {
    @Resource
    private CompanyMapper mapper;

    @PostMapping("/certCompany")
    public void certCompany(@CurrentUser SysUser sysUser, @RequestBody Company company) throws ForeseenException {
        Company cert = mapper.selectOne(Company.builder().userId(sysUser.getId()).build());
        if (cert != null && !cert.getStatus().equals(2)) {
            throw new ShowException("您已经提交企业认证了");
        }
        if (cert != null) {
            company.setId(cert.getId());
            mapper.updateByPrimaryKeySelective(company);
        } else {
            company.setUserId(sysUser.getId().intValue());
            mapper.insertSelective(company);
        }
    }

}
