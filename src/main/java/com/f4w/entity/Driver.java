package com.f4w.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Table;

/**
 * @Author: yp
 * @Date: 2020/9/8 15:28
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`driver`")
@EqualsAndHashCode(callSuper = true)
public class Driver extends BaseEntity {
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
    private String remark;
}
