package com.bling.dab.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: hxp
 * @date: 2019/4/4 10:00
 * @description:
 */
@Data
@ApiModel(value = "signIn",description = "账户")
public class SignIn {

    @ApiModelProperty(name = "id",dataType = "String",example = "1")
    private String id;
    @ApiModelProperty(name = "userName",dataType = "String",example = "San")
    private String userName;
    @ApiModelProperty(name = "userName",dataType = "String",example = "123456")
    private String password;


}
