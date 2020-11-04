package com.f4w.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ColumnWidth(30)
public class TalentPool extends BaseEntity {
    @ExcelProperty(value = "id",index = 0)
    private String pid;
    private String tid;
    @ExcelProperty(value ="姓名",index = 1)
    private String name;
    @ExcelProperty(value ="手机号",index = 2)
    private String phone;
    @ExcelProperty(value ="毕业院校",index = 3)
    private String sc;
    @ExcelProperty(value ="基本信息",index = 4)
    private String base;
    private String education;
    @ExcelProperty(value ="刷新时间",index = 5)
    private Date updateTime;
    private String major;
    private String remark;
    private Integer status;

}
