package com.f4w.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: yp
 * @Date: 2020/9/22 19:13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDaysResp {
    private Long num;
    private Date time;
}
