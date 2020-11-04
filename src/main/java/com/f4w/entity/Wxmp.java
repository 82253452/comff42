package com.f4w.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


/**
 * createBy:2019-01-07 17:05:51
 *
 * @author yp
 */
@Table(name = "`wxmp`")
@Data
public class Wxmp extends BaseEntity {

    /**
     * 文章类型
     */
    private Integer type;
    /**
     * 标题
     */
    private String title;
    /**
     * 缩略图
     */
    private String thumbnail;
    /**
     * 摘要
     */
    private String summary;
    /**
     * 视频ID
     */
    private String videoId;
    /**
     * 作者
     */
    private String auther;
    /**
     * 栏目
     */
    private Integer columnId;
    /**
     * 状态0 默认 1已采 2已发
     */
    private Integer status;

    private String content;

    private Integer del;

    private Integer isTop;

}
