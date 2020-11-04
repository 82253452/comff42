package com.f4w.utils;

/**
 * @author : houxm
 * @date : 2019/4/4 16:07
 * @description :用于前端展示异常弹窗
 */
public class ShowException extends ForeseenException {
    public ShowException(int code, String message) {
        super(code, message);
    }

    public ShowException(String message) {
        super(message);
    }
    public ShowException(SystemErrorEnum systemErrorEnum) {
        super(systemErrorEnum);
    }
}
