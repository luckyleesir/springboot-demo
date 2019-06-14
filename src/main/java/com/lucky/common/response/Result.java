package com.lucky.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: lucky
 * @date: 2019/6/12 16:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result implements Serializable {
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
    private Object data;

    public static Result success(Object data) {
        return Result.builder().code(200).msg("成功").data(data).build();
    }

    public static Result error(Object data) {
        return Result.builder().code(500).msg("失败").data(data).build();
    }
}
