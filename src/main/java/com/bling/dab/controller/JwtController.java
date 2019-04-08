package com.bling.dab.controller;

import com.bling.dab.domain.SignIn;
import com.bling.dab.service.SignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by Administrator on 2019/2/23 0023.
 */
@ApiIgnore
@RestController
public class JwtController {

    @Autowired
    private SignInService signInService;

    @RequestMapping(value="/jwt")
    public String jwt(){

        return "jwt.html";
    }

    @RequestMapping(value="/signIn")
    public String signIn(SignIn in){
        SignIn signIn = signInService.querySignIn();
        if(null != signIn){
            return "success.html";
        }
        return "error.html";
    }

}
