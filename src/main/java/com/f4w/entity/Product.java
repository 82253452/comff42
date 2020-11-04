package com.f4w.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @Author: yp
 * @Date: 2020/9/8 15:28
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`product`")
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity {
    private String name;
    private BigDecimal price;
    private Integer num;
    private String img;
    private String detail;
    private String remark;
    private Integer status;
    private Integer sort;
    private Integer type;
}
