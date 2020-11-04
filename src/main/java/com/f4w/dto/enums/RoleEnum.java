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
public enum RoleEnum {
    ADMIN("admin", "", "管理员"),
    TRANS("trans", "transId", "物流公司"),
    PERSON("user", "userId", "个人"),
    COMPANY("company", "companyId", "企业");

    private final String code;
    private final String column;
    private final String description;
}
