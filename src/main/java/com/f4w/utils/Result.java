package com.f4w.utils;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yp
 */
@Data
public class Result<T> {
    private boolean success = true;

    private Integer code;

    private String message;

    private String showMsg;

    private T data;

    public static <T> Result<T> render(SystemErrorEnum resultEnum) {
        return generate(resultEnum, null, "");
    }

    public static <T> Result<T> ok() {
        return generate(SystemErrorEnum.SUCCESS, null, "");
    }

    public static <T> Result<T> ok(T data) {
        return generate(SystemErrorEnum.SUCCESS, data, "");
    }

    public static <T> Result<T> error(SystemErrorEnum resultEnum, String message) {
        return generate(resultEnum, null, message);
    }


    public static <T> Result<T> generate(SystemErrorEnum resultEnum, T data, String message) {
        Result result = new Result<T>();
        result.setCode(resultEnum.getCode());
        result.setData(data);
        result.setMessage(resultEnum.getMessage());
        result.setSuccess(resultEnum.getCode().equals(0) || resultEnum.getCode().equals(1000));
        result.setShowMsg(StringUtils.isBlank(message) ? resultEnum.getShowMessage() : message);
        return result;
    }

}
