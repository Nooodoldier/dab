package com.bling.dab.controller;

import com.bling.dab.common.annotation.LoginToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author: hxp
 * @date: 2019/6/21 15:54
 * @description:
 */
@ApiIgnore
@Controller
@RequestMapping("/echarts")
public class EchartsController {

    /**
     * @LoginToken
     * shiro控制用户权限
     * jwt控制用户登陆状态
     * 必须是登陆状态才能访问的路径必须加token校验
     * 1不需要登陆状态的请求路径或测试的路径需要在拦截器设置排除拦截和在shiro配置不设拦截（因为shiro判断也是在登陆状态的前提下判断用户的权限）
     * 2只需要校验token的类和登陆类统一shiro配置不设拦截即可，但分别需要配置校验token的注解和登陆注解（我的这个登陆注解其实没有必要，有校验的就可以了）
     * 3判断shiro的类必须有校验token的注解，先判断token在判断shiro权限
     *
     * 此处不要要登陆即可访问在shiro配置不拦截即可，登陆注解可有可无
     * @return
     */

    @RequestMapping(value="/xAxis",method = RequestMethod.GET)
    public String xAxis(){

        return "xAxis.html";

    }
}
