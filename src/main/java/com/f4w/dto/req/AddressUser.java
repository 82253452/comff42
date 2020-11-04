package com.f4w.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: yp
 * @Date: 2020/10/13 19:13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressUser {
    private String address;
    private String name;
    private String phone;
}
