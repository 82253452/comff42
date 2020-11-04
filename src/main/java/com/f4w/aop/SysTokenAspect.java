package com.f4w.aop;

import com.f4w.utils.JWTUtils;
import com.f4w.utils.R;
import com.f4w.utils.Result;
import com.f4w.utils.SystemErrorEnum;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;


/**
 * @author admin
 */
@Aspect
@Component
public class SysTokenAspect {
    public static final String TOKEN = "X-Token";
    @Resource
    private JWTUtils jwtUtils;

    @Pointcut("@annotation(com.f4w.annotation.NotTokenIntecerpt)")
    private void notTokenIntercept() {
    }

    @Pointcut("execution(public * com.f4w.controller..*(..))")
    private void tokenIntercept() {
    }

    @Pointcut("execution(public * com.f4w.carAdminController..*(..))")
    private void tokenCarIntercept() {
    }

    @Pointcut("execution(public * com.f4w.carApiController..*(..))")
    private void tokenCarApiIntercept() {
    }

    @Pointcut("!notTokenIntercept() && tokenIntercept() && tokenCarIntercept() && tokenCarApiIntercept()")
    public void intercept() {

    }

    @Around(value = "intercept()")
    public Object methodAround(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader(TOKEN);
        if (StringUtils.isBlank(token)) {
            return Result.render(SystemErrorEnum.AUTH_EXP);
        }
        try {
            jwtUtils.parseBody(token);
        } catch (Exception e) {
            return Result.render(SystemErrorEnum.AUTH_EXP);
        }
        return pjp.proceed();
    }

}
