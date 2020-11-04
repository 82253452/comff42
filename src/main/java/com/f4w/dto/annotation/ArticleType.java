package com.f4w.dto.annotation;

import com.f4w.dto.enums.ArticleTypeEnum;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited  //子类可以继承此注解
public @interface ArticleType {
    ArticleTypeEnum value();
}
