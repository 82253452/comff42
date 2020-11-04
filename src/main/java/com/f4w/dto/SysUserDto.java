package com.f4w.dto;

import com.f4w.entity.Company;
import com.f4w.entity.Driver;
import com.f4w.entity.SysUser;
import com.f4w.entity.TransCompany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author admin
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysUserDto extends SysUser {
    private String token;
    /**
     * 用户所属物流公司
     */
    private List<TransCompany> transCompanyList;
    /**
     * 用户企业信息
     */
    private Company company;
    /**
     * 司机认证
     */
    private Driver driver;
    /**
     * 用户物流公司信息
     */
    private TransCompany transCompany;
}
