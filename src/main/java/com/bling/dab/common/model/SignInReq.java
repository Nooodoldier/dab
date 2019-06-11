package com.bling.dab.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: hxp
 * @date: 2019/6/11 17:02
 * @description:
 */
@Data
@ApiModel(value = "SignInReq",description = "账户")
public class SignInReq {
    @ApiModelProperty(name = "id",dataType = "String",example = "1")
    private String id;
    @ApiModelProperty(name = "userName",dataType = "String",example = "San")
    private String userName;
    @ApiModelProperty(name = "password",dataType = "String",example = "123456")
    private String password;
}
