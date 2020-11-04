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
@Table(name = "`trans_company`")
@EqualsAndHashCode(callSuper = true)
public class TransCompany extends BaseEntity {
    private String name;
    private String businessLicense;
    private String legalPerson;
    private String legalPhone;
    private String address;
    private String contactsPerson;
    private String contactsPhone;
    private String email;
    private String remark;
    private Integer userId;
    private Integer status;
}
