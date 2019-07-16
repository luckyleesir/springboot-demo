package com.lucky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 查询条件基类
 *
 * @author: lucky
 * @date: 2019/7/16 9:29
 */
@ApiModel(value = "BaseQueryDto对象", description = "查询条件基类")
@Data
public class BaseQueryDto implements Serializable {

    @ApiModelProperty(value = "创建时间开始")
    private LocalDateTime createTimeFrom;

    @ApiModelProperty(value = "创建时间结束")
    private LocalDateTime createTimeTo;

    @ApiModelProperty(value = "更新时间开始")
    private LocalDateTime updateTimeFrom;

    @ApiModelProperty(value = "更新时间结束")
    private LocalDateTime updateTimeTo;
}
