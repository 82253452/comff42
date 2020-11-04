package com.f4w.test;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DataDemo {
    @ExcelProperty("导入id")
    private String id;
    @ExcelProperty("头像")
    private String logo;
    @ExcelProperty("简介")
    private String desc;
    @ExcelProperty("公众号名称")
    private String bizName;
    @ExcelProperty("微信id")
    private String wechatId;
    @ExcelProperty("二维码")
    private String qrCode;
    @ExcelProperty("注册时间")
    private String regDateTime;
    @ExcelProperty("西瓜指数")
    private String guaZhiShu;
    @ExcelProperty("主体")
    private String weiXinAuthInfo;
    @ExcelProperty("预估活跃粉丝")
    private Integer fans;
    @ExcelProperty("头条平均阅读")
    private Integer firstAvgRead;
    @ExcelProperty("头条平均点赞")
    private Integer firstAvgLike;
    @ExcelProperty("头条平均评论")
    private Integer firstAvgComment;

}

