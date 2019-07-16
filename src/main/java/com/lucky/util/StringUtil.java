package com.lucky.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字符串工具类
 *
 * @author: lucky
 * @date: 2019/7/16 15:04
 */
public class StringUtil {

    /**
     * 逗号分隔字符串转List
     *
     * @param str 逗号分隔字符串
     * @return List
     */
    public static List<Long> getList(String str) {
        return Arrays.stream(str.split(",")).map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
    }
}
