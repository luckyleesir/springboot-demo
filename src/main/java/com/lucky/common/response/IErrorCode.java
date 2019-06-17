package com.lucky.common.response;

/**
 * 封装API的错误码
 *
 * @author lucky
 */
public interface IErrorCode {
    /**
     * 返回code
     *
     * @return code
     */
    int getCode();

    /**
     * 返回信息
     *
     * @return msg
     */
    String getMsg();
}
