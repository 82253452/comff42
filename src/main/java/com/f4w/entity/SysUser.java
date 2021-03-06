package com.f4w.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import java.util.Date;

/**
 * @author admin
 */
@EqualsAndHashCode(callSuper = true)
@Table(name = "`sys_user`")
@Data
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

}