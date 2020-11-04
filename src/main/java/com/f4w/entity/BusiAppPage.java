package com.f4w.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;


/**
 * createBy:2018-11-02 11:14:00
 *
 * @author yp
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`busi_app_page`")
@EqualsAndHashCode(callSuper = true)
public class BusiAppPage extends BaseEntity {
    private String pageName;
    private String appId;
    private String appSecret;
    private String content;
    private Integer uid;
    private Integer custom;
}
