package com.f4w.carApiController;

import com.f4w.annotation.CurrentUser;
import com.f4w.entity.Driver;
import com.f4w.entity.SysUser;
import com.f4w.mapper.DriverMapper;
import com.f4w.utils.ShowException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: yp
 * @Date: 2020/9/8 10:57
 */
@RestController
@RequestMapping("/api/driver")
public class ApiDriverController {
    @Resource
    private DriverMapper driverMapper;

    @PostMapping("/register")
    public void register(@CurrentUser SysUser sysUser, @RequestBody Driver driver) throws ShowException {
        Driver driverS = driverMapper.selectOne(Driver.builder().userId(sysUser.getId()).build());
        if (driverS != null && driverS.getStatus() == 2) {
            throw new ShowException("您已经提交司机认证了");
        }
        if (driverS != null) {
            driver.setId(driverS.getId());
            driverMapper.updateByPrimaryKeySelective(driver);
        } else {
            driver.setUserId(sysUser.getId());
            driverMapper.insertSelective(driver);
        }
         driverMapper.insertSelective(driver);

    }

}
