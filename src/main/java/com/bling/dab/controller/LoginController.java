package com.bling.dab.controller;

import com.bling.dab.common.annotation.Auth;
import com.bling.dab.common.result.Result;
import com.bling.dab.domain.User;
import com.bling.dab.service.LoginService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: hxp
 * @date: 2019/2/18 10:56
 * @description:
 */
@RestController
@RequestMapping("/login")
public class LoginController {


    @Autowired
    private LoginService loginService;
    @RequestMapping(value="/index")
    public ModelAndView index(){
        User user = new User();
        User tom = User.builder().name("tom").age(10).build();
        ModelAndView mv = new ModelAndView();
        mv.addObject("user", tom);
        mv.setViewName("/index.html");
        return mv;
    }

}
