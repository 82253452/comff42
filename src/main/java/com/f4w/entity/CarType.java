package com.f4w.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Table;

/**
 * @Author: yp
 * @Date: 2020/9/8 15:28
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`car_type`")
@EqualsAndHashCode(callSuper = true)
public class CarType extends BaseEntity {
    private String title;
    private Integer type;
    private Integer carLength;
    private Integer carWidth;
    private Integer carHeight;
    private Integer carryingCapacity;
    private String img;
}
