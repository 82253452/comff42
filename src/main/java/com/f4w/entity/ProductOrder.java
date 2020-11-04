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
@Table(name = "`product_order`")
@EqualsAndHashCode(callSuper = true)
public class ProductOrder extends BaseEntity {
    private String orderNo;
    private Integer orderType;
    private Integer userId;
    private Integer productId;
    private BigDecimal originalPrice;
    private BigDecimal amount;
    private String des;
    private Integer status;
    private String remark;
}
