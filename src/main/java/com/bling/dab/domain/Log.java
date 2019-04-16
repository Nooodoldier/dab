package com.bling.dab.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author: hxp
 * @date: 2019/4/15 20:14
 * @description:日志
 */
@Data
public class Log {

    private int id;
    private String modelName;
    private String action;
    private String description;
    private String ip;
    private Date createTime;
    private String success;
    private String message;
    private int userId;
    private String className;
    private String methodName;
    private String methodArgs;
}
