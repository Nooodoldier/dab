package com.bling.dab.common.annotation;

import java.lang.annotation.*;

/**
 * @author: hxp
 * @date: 2019/4/12 15:33
 * @description:在方法上加上该注解来配合实现记录日志
 */
@Target( { ElementType.METHOD } )
@Retention( RetentionPolicy.RUNTIME )
@Documented
public @interface Log {

    /**
     * 模块名称
     * @return
     */
    String modelName() default "";

    /**
     * 操作
     * @return
     */
    String action() default "";

    /**
     * 描述
     * @return
     */
    String description() default "";
}
