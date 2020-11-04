package com.f4w.annotation;


import java.lang.annotation.*;


/**
 * @author yp
 */
@Target({ElementType.PARAMETER})//定义注解的作用目标**作用范围字段、枚举的常量/方法
@Retention(RetentionPolicy.RUNTIME)
@Documented//说明该注解将被包含在javadoc中
public @interface CurrentUser {

}
