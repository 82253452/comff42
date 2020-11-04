package com.f4w.dto.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author yp
 */
@Data
public class MiniAppLoginReq {
    @NotBlank
    private String code;
    @NotBlank
    private String signature;
    @NotBlank
    private String rawData;
    @NotBlank
    private String encryptedData;
    @NotBlank
    private String iv;
    @NotBlank
    private String appId;
}
