package com.f4w.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`columns`")
public class Columns extends BaseEntity {
    /**
     * 类型
     */
    @Column(name = "`type`")
    private Integer type;

    /**
     * 名称
     */
    @Column(name = "title")
    private String title;

    /**
     * 编号
     */
    @Column(name = "code")
    private String code;

    /**
     * 状态0 默认 1已采 2已发
     */
    @Column(name = "`status`")
    private Integer status;
}