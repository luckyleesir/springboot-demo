package com.lucky.common.enums;

/**
 * 权限类型
 *
 * @author: lucky
 * @date: 2019/6/29 16:46
 */
public enum PermissionType {

    MENU((byte) 1, "菜单");

    private Byte code;
    private String name;

    PermissionType(Byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
