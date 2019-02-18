package com.bling.dab.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author: hxp
 * @date: 2019/2/18 10:56
 * @description:
 */
@RestController
@ApiIgnore
public class LoginController {

    @RequestMapping(value="/login")
    public String hello(){
        return "login";
    }
}
