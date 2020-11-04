package com.f4w.dto;

import com.f4w.entity.SysUser;
import com.f4w.entity.TransCompany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @Author: yp
 * @Date: 2020/9/17 16:16
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TransCompanyDto extends TransCompany {
    private Integer status;
}
