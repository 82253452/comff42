package com.f4w.dto;

import com.f4w.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * createBy:2018-11-06 20:32:50
 *
 * @author yp
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderInfoDto extends Order {
    private String title;
    private Integer type;
    private Integer carLength;
    private Integer carWidth;
    private Integer carHeight;
    private Integer carryingCapacity;
}
