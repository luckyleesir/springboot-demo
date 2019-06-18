package com.lucky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: lucky
 * @date: 2019/6/12 16:06
 */
@ApiModel(value = "用户参数")
@Data
public class UserLoginDto {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;
}
