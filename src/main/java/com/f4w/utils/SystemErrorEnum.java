package com.f4w.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author yp
 */

@Getter
@ToString
@RequiredArgsConstructor
public enum SystemErrorEnum {

    SUCCESS(0, "成功", ""),
    SYSTEM_ERROR(1, "系统异常", ""),
    ARGUMENT_VALID(2, "参数校验错误", ""),
    AUTH_EXP(3, "身份过期", ""),
    AUTH_TOKEN(4, "无效的token", ""),
    NULL(1001, "空指针异常", ""),
    RUNTIME_EXCEPTION(1002, "异常", ""),
    NOT_FOUND_INDEX(1001, "id 不存在", ""),
    USER_ERROR(1003, "用户名或密码错误", "用户名或密码错误"),
    USER_DEL_ERROR(1004, "删除失败", "超级管理员不可以删除"),
    USER_EXP(100, "登录失效", ""),
    HOUR(4, "小时", "");

    private final Integer code;
    private final String message;
    private final String showMessage;
}
