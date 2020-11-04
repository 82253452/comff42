package com.f4w.utils;

import com.xxl.job.core.biz.model.ReturnT;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author houxm
 * @version 1.01 2018/4/17 19:33
 * @description
 */
@RestControllerAdvice("com.f4w")
@Slf4j
public class ExceptionHandlerAdvice {
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public Object handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex, final ServletWebRequest req) {
        List<String> errorMap = this.validRequestParams(ex.getBindingResult());
        return Result.error(SystemErrorEnum.ARGUMENT_VALID, errorMap.get(0));
    }

    /**
     * @return :
     * @Description: 统一异常处理 用于@RequestParam 的参数校验
     * @auther : wujingxiong
     * @date : 2019/3/18 15:43
     */
    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public Object handleMissingServletRequestParameterException(final MissingServletRequestParameterException ex, final ServletWebRequest req) {
        return Result.error(SystemErrorEnum.ARGUMENT_VALID, ex.getMessage());
    }

    /**
     * @return :
     * @Description: 统一异常处理 用于@RequestBody 的参数校验
     * @auther : wujingxiong
     * @date : 2019/3/18 15:43
     */
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public Object handleHttpMessageNotReadableException(final HttpMessageNotReadableException ex, final ServletWebRequest req) {
        return Result.error(SystemErrorEnum.ARGUMENT_VALID, "非法JSON字符串");
    }

    private List<String> validRequestParams(BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            List<String> lists = new ArrayList<>();
            for (ObjectError objectError : allErrors) {
                lists.add(objectError.getDefaultMessage());
            }
            return lists;
        }
        return null;
    }

    @ExceptionHandler(value = {ShowException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public Object handleShowExceptions(final Exception ex, final ServletWebRequest req) {
        ForeseenException fex = (ForeseenException) ex;
        return Result.error(SystemErrorEnum.NULL,ex.getMessage());
    }

    @ExceptionHandler(value = {NullPointerException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public Object handleNullPointerException(final Exception ex, final ServletWebRequest req) {
        return Result.render(SystemErrorEnum.NULL);
    }

    @ExceptionHandler(value = {ForeseenException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public Object handleCustomerExceptions(final Exception ex, final ServletWebRequest req) {
        ForeseenException fex = (ForeseenException) ex;
        return Result.error(SystemErrorEnum.SYSTEM_ERROR, ex.getMessage());
    }

    @ExceptionHandler(value = {BindException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public Object handleBindExceptions(final Exception ex, final ServletWebRequest req) {
        return Result.render(SystemErrorEnum.ARGUMENT_VALID);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public Object handleConstraintViolationExceptions(final Exception ex, final ServletWebRequest req) {
        return Result.error(SystemErrorEnum.ARGUMENT_VALID, "");
    }

    @ExceptionHandler(value = {ExpiredTokenException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public Object expiredTokenException(final Exception ex, final ServletWebRequest req) {
        return Result.render(SystemErrorEnum.AUTH_EXP);
    }

    @ExceptionHandler(value = {ExpiredJwtException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public Object expiredJwtException(final Exception ex, final ServletWebRequest req) {
        return Result.render(SystemErrorEnum.AUTH_EXP);
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.OK)
    public Object handleOtherExceptions(final Exception ex, final ServletWebRequest req) {
        return Result.error(SystemErrorEnum.SYSTEM_ERROR, ex.getMessage());
    }

    @ExceptionHandler(value = {JobException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public Object handleJobException(final Exception ex, final ServletWebRequest req) {
        log.error("定时任务执行异常---", ex.getMessage());
        return new ReturnT<>(ex.getMessage());
    }

    /**
     * @return :
     * @Description: 获取 注解校验中的所有异常信息;
     * @auther : wujingxiong
     * @date : 2019/3/18 16:21
     */
    private String getValidError(BindingResult bindingResult) {
        StringBuffer sb = new StringBuffer();
        for (ObjectError objectError : bindingResult.getAllErrors()) {
            sb.append(((FieldError) objectError).getField() + " : ").append(objectError.getDefaultMessage() + "; ");
        }
        return sb.toString();
    }
}
