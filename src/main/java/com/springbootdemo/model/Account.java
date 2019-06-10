package com.springbootdemo.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class Account implements Serializable {
    /**
     * 账号id
     */
    private Long userId;

    /**
     * 账号
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 昵称
     */
    private String nick;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date modifyTime;

    private static final long serialVersionUID = 1L;
}