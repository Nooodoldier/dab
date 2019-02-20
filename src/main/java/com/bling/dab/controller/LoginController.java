package com.bling.dab.controller;

import com.bling.dab.domain.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author: hxp
 * @date: 2019/2/18 10:56
 * @description:
 */
@ApiIgnore
@RestController
public class LoginController {

    @RequestMapping(value="/index")
    public ModelAndView index(){
        User user = new User();
        user.setName("tom");
        user.setAge(10);
        ModelAndView mv = new ModelAndView();
        mv.addObject("user", user);
        mv.setViewName("/index.html");
        return mv;
    }
}
