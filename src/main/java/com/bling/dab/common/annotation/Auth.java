package com.bling.dab.common.annotation;

import java.lang.annotation.*;

/**
 * @author: hxp
 * @date: 2019/4/24 19:59
 * @description:在类或方法上添加@Auth就验证登录
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {

}
