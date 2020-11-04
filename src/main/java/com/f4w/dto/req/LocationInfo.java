package com.f4w.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: yp
 * @Date: 2020/10/13 20:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationInfo {
    private String province;
    private String city;
    private String district;
    private Integer city_code;
    private String name;
    private Integer adcode;
    private Integer nation_code;
}
