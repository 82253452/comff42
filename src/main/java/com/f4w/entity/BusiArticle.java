package com.f4w.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


/**
 * createBy:2018-11-02 11:14:01
 *
 * @author yp
 */
@Table(name = "`busi_article`")
@Data
public class BusiArticle extends BaseEntity {

    /**
     *
     */
    private Integer uid;
    /**
     *
     */
    private Integer recommend;
    /**
     *
     */
    private String img;
    /**
     *
     */
    private String title;

    private String content;

    private Integer readNum;

    private String appId;

    private String tag;

    private Integer weight;

}
