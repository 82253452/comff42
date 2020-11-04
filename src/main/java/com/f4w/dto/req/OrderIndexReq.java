package com.f4w.dto.req;

import com.f4w.dto.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @Author: yp
 * @Date: 2020/10/13 19:13
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OrderIndexReq extends CommonPageReq{
    private String[] reginFrom;
    private String[] reginTo;
    private String keyWrod;
}
