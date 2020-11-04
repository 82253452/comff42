package com.f4w.utils;

/**
 * @author : houxm
 * @date : 2018/10/24 10:09
 * @description :
 */
public class ResultCode {
    /**
     * 没有权限
     */
    public static final int USER_NO_AUTH = 1004;
    /**
     * 数据重复，比如手机号
     */
    public static final int DATA_EXIST = 99;

    public static final int RESULT_SUCCESS = 0;

    public static final int AUTH_FAILED = 100;//未登录

    public static final int DATA_NOT_EXIST = 4; // 目标未找到不存在或已删除

    public static final int PARAMS_ERROR = 2; // 参数不合法

    public static final int RESULT_FAILURE = 1;//失败

    public static final int NO_PHONE = 4000;//未绑定手机号

    /**
     * 不足
     */
    public static final int NOT_ENOUGH = 3;

    /**
     * 未关注
     */
    public static final int UNFOLLOWED = 999;


    /**
     * 后台使用
     */
    public static final int PASSWORD_ERROR = 5005; // 参数不合法

    /**
     * 当前用户需要资产合并
     */
    public static final Integer USER_BIND_MERGE_ERROR = 1001;
    /**
     * 手机验证码错误
     */
    public static final Integer USER_BIND_CODE_ERROR = 1002;
    /**
     * 手机号已经绑定过此类三方
     */
    public static final Integer USER_BIND_PHONE_ERROR = 1003;
    /**
     * 微信号已经绑定过手机
     */
    public static final Integer USER_BIND_WECHAT_ERROR = 1004;
    /**
     * 验证超时
     */
    public static final Integer USER_BIND_VERIFY_ERROR = 1005;
}
