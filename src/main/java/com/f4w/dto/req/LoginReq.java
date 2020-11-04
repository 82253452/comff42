package com.f4w.dto.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: yp
 * @Date: 2020/9/22 19:09
 */
@Data
public class LoginReq {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
