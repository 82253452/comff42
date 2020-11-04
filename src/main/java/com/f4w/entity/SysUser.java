package com.f4w.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author admin
 */
@EqualsAndHashCode(callSuper = true)
@Table(name = "`sys_user`")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SysUser extends BaseEntity {
    private String openid;

    private String nickname;

    private Integer gender;

    private String language;

    private String city;

    private String province;

    private String country;

    private String avatarurl;

    private String unionid;

    private String appId;

    private String userName;

    private String password;

    private String phone;

    private BigDecimal amount;

    private Integer creditScore;

    private Integer type;

    private Integer integral;

}
