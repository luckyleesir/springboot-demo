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
     * 当未登录或者token失效访问接口时，拋出由spring security处理
     */
    @ExceptionHandler(AuthenticationException.class)
    public Result authenticationException(Throwable ex) throws Throwable {
        log.error(ex.toString());
        throw ex;
    }

    /**
     * 当访问接口没有权限时，拋出由spring security处理
     */
    @ExceptionHandler(AccessDeniedException.class)
    public void accessDeniedException(Throwable ex) throws Throwable {
        log.error(ex.toString());
        throw ex;
    }

    /**
     * 捕捉其他所有异常
     * 开发时注释掉，方便debug看错误信息
     */
    //@ExceptionHandler(Exception.class)
    public Result globalException(Throwable ex) {
        log.error(ex.toString());
        return Result.failed(ex.getMessage());
    }

}

