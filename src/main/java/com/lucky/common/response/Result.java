package com.lucky.common.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回结果
 *
 * @author: lucky
 * @date: 2019/6/12 16:48
 */
@Data
@Builder
public class Result<T> implements Serializable {
    /**
     * 返回code
     */
    private int code;
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 返回结果集合
     */
    private T data;


    protected Result() {
    }

    protected Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    /**
     * 成功返回结果
     */
    public static <T> Result<T> success() {
        return success(null);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param msg  提示信息
     */
    public static <T> Result<T> success(T data, String msg) {
        return new Result<>(ResultCode.SUCCESS.getCode(), msg, data);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     */
    public static <T> Result<T> failed(IErrorCode errorCode) {
        return new Result<>(errorCode.getCode(), errorCode.getMsg(), null);
    }

    /**
     * 失败返回结果
     *
     * @param msg 提示信息
     */
    public static <T> Result<T> failed(String msg) {
        return new Result<>(ResultCode.FAILED.getCode(), msg, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> Result<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> Result<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param msg 提示信息
     */
    public static <T> Result<T> validateFailed(String msg) {
        return new Result<>(ResultCode.VALIDATE_FAILED.getCode(), msg, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> Result<T> unauthorized(T data) {
        return new Result<>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMsg(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> Result<T> forbidden(T data) {
        return new Result<>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMsg(), data);
    }
}
