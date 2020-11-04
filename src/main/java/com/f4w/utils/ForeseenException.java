package com.f4w.utils;

import lombok.Data;

/**
 * @author houxm
 * @version 1.01 2018/4/16 11:20
 * @description 统一自定义异常类
 */
@Data
public class ForeseenException extends Exception {
    private int code = ResultCode.RESULT_FAILURE;
    private SystemErrorEnum errorEnum;


    public ForeseenException() {

    }

    public ForeseenException(SystemErrorEnum systemErrorEnum) {
        this.errorEnum = systemErrorEnum;
    }

    public ForeseenException(String message) {
        super(message);
    }

    public ForeseenException(int code) {
        this.code = code;
    }

    public ForeseenException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ForeseenException(Exception ex) {
        super(ex);
        if (ex instanceof ForeseenException) {
            this.code = ((ForeseenException) ex).getCode();
        } else {
            this.code = ResultCode.RESULT_FAILURE;
        }
    }
}
