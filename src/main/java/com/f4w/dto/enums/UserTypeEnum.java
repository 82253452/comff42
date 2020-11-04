package com.f4w.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author admin
 */
@Getter
@ToString
@RequiredArgsConstructor
public enum UserTypeEnum {
    SYSTEM(0, "系统用户"),
    TRANS_COMPANY(1, "物流公司"),
    DRIVER(2, "司机"),
    COMPANYH(3, "企业");

    private final Integer code;
    private final String description;
}
