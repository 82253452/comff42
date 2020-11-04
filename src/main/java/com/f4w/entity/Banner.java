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
@Table(name = "`banner`")
@EqualsAndHashCode(callSuper = true)
public class Banner extends BaseEntity {
    private String title;
    private String src;
    private String href;
    private Integer type;
}
