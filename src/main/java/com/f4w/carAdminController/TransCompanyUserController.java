package com.f4w.carAdminController;

import com.f4w.annotation.CurrentUser;
import com.f4w.dto.SysRoleDto;
import com.f4w.dto.TransCompanyUserDto;
import com.f4w.dto.annotation.PermisionRole;
import com.f4w.dto.enums.RoleEnum;
import com.f4w.dto.req.CommonPageReq;
import com.f4w.dto.req.TransPageReq;
import com.f4w.entity.Driver;
import com.f4w.entity.SysUser;
import com.f4w.entity.TransCompany;
import com.f4w.mapper.DriverMapper;
import com.f4w.mapper.SysRoleMapper;
import com.f4w.mapper.TransCompanyMapper;
import com.f4w.mapper.TransCompanyUserMapper;
import com.f4w.utils.ShowException;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: yp
 * @Date: 2020/9/8 10:57
 */
@RestController
@RequestMapping("/transCompanyUser")
public class TransCompanyUserController {
    @Resource
    private TransCompanyUserMapper mapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private TransCompanyMapper transCompanyMapper;
    @Resource
    private DriverMapper driverMapper;

    @GetMapping("/list")
    public PageInfo<TransCompanyUserDto> list(CommonPageReq req) {
        PageInfo<TransCompanyUserDto> page = PageInfo.of(mapper.getList(req));
        return page;
    }

    @GetMapping("/admin/list")
    @PermisionRole
    public PageInfo<TransCompanyUserDto> adminList(TransPageReq req) throws ShowException {
        PageInfo<TransCompanyUserDto> page = PageInfo.of(mapper.getAdminList(req));
        return page;
    }

    @GetMapping("/checkUser")
    public void checkUser(Integer id, Integer status) {
        Driver driver = driverMapper.selectByPrimaryKey(id);
        if (driver.getStatus().equals(0) || driver.getStatus().equals(2)) {
            driver.setStatus(status);
            driverMapper.updateByPrimaryKeySelective(driver);
        }
    }

    @DeleteMapping("{id}")
    public int delete(@PathVariable Integer id) {
        return transCompanyMapper.deleteByPrimaryKey(id);
    }

}
