package com.f4w.utils;


import org.apache.commons.collections4.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author houxm
 * @version 1.01 2018/5/30 15:13
 * @description
 */
public final class ValidateUtils {
    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    public static <T> List<String> validate(T t) {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
        List<String> messageList = new ArrayList<>();
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            messageList.add(constraintViolation.getMessage());
        }
        return messageList;
    }

    public static <T> void validateThrowsForeseenException(T t) throws ForeseenException {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
        if (constraintViolations == null || constraintViolations.isEmpty())
            return;
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            throw new ForeseenException(ResultCode.PARAMS_ERROR, constraintViolation.getMessage());
        }
    }

    public static <T> void validateThrowsJobException(T t) throws JobException {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
        if (CollectionUtils.isNotEmpty(constraintViolations)) {
            throw new JobException(constraintViolations.stream().findFirst().get().getMessage());
        }

    }
}
