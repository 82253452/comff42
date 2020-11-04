package com.f4w.test;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ColumnWidth(30)
public class PersonData{
    @Column(name = "pid")
    @ExcelProperty("id")
    private String id;
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("手机号")
    private String phone;
    @ExcelProperty("毕业院校")
    private String sc;
    @ExcelProperty("基本信息")
    private String base;

}
