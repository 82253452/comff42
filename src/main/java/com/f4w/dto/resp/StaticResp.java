package com.f4w.dto.resp;

import com.f4w.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: yp
 * @Date: 2020/9/22 19:13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaticResp {
    private Long preOrderNum;
    private Long totalOrderNum;
    private Long driverNum;
    private Long transNum;
}
