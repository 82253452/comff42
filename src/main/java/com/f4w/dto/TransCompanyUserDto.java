package com.f4w.dto;

import com.f4w.entity.SysUser;
import lombok.*;
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
public class TransCompanyUserDto extends SysUser {
    private String name;
    private String carNumber;
    private String carModel;
    private String phone;
    private String carImg;
    private String userImg;
    private Integer drivingExperience;
    private String drivingLicense;
    private String driversLicense;
    private Integer transId;
    private Integer userId;
    private Integer status;
    private Integer driverStatus;
    private String remark;
    private String transName;
}
