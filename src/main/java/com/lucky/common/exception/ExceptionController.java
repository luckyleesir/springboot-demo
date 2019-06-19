package com.lucky.common.exception;

import com.lucky.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author lucky
 */
@Slf4j
@RestControllerAdvice
public class ExceptionController {

    /**
     * 当未登录或者token失效访问接口时，自定义的返回结果
     */
    @ExceptionHandler(AuthenticationException.class)
    public Result authenticationException(Throwable ex) {
        log.error(ex.toString());
        return Result.unauthorized(ex.getMessage());
    }

    /**
     * 当访问接口没有权限时，自定义的返回结果
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result accessDeniedException(Throwable ex) {
        log.error(ex.toString());
        return Result.forbidden(ex.getMessage());
    }

    /**
     * 捕捉其他所有异常
     */
    @ExceptionHandler(Exception.class)
    public Result globalException(Throwable ex) {
        log.error(ex.toString());
        return Result.failed(ex.getMessage());
    }

}

