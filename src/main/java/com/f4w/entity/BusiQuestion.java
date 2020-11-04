package com.f4w.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


/**
 * createBy:2018-11-19 15:18:38
 * @author yp
 */
@Table(name = "`busi_question`")
@Data
public class BusiQuestion extends BaseEntity {

    /**
     *
     */
    private String title;
    /**
     *
     */
    private String questions;
    /**
     *
     */
    private Integer status;
    /**
     * 正确答案
     */
    private String answer;
    /**
     *
     */
    private String appId;
    /**
     *
     */
    private Integer uid;

    private Integer type;

}
