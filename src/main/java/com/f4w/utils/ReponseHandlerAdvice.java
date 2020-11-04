package com.f4w.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * @author yp
 */
@Slf4j
@RestControllerAdvice("com.f4w")
public class ReponseHandlerAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o == null) {
            return Result.ok();
        }
        if (o instanceof Result || o instanceof R) {
            return o;
        }
        if ("byte[]".equals(o.getClass().getTypeName())) {
            return o;
        }
        return Result.ok(o);
    }
}


