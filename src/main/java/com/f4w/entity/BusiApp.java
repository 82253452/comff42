package com.f4w.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizerInfo;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;


/**
 * createBy:2018-11-02 11:14:00
 *
 * @author yp
 */
@Table(name = "`busi_app`")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BusiApp extends BaseEntity {

    private String appId;
    private Long pageId;
    //1 生成模板成功 2生成模板中 3 生成模板失败
    //0 默认 1授权成功 2发版审核中 3审核通过 4审核失败 5发布成功 6发布失败 7上传成功(发版失败)
    private Integer status;
    private String fileName;
    private Integer uid;
    private Long auditId;
    private Integer version;
    private String auditMsg;
    private Integer replay;
    private String nickName;
    private String headImg;
    private Integer serviceTypeInfo;
    private Integer verifyTypeInfo;
    private String userName;
    private String principalName;
    private String qrcodeUrl;
    private String signature;
    private Integer miniProgramInfo;
    private Integer autoMessage;
    private Integer messageId;
    private String messageParam;
    private String headerText;
    private String footerText;
}
