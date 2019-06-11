package com.bling.dab.controller;

import com.alibaba.fastjson.JSONObject;
import com.bling.dab.common.annotation.CheckToken;
import com.bling.dab.common.annotation.LoginToken;
import com.bling.dab.common.util.JwtTokenUtil;
import com.bling.dab.domain.SignIn;
import com.bling.dab.service.SignInService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: hxp
 * @date: 2019/5/10 15:45
 * @description:
 */
@Api(description = "token分配管理")
@RestController
@RequestMapping("/token")
public class TokenController {


    @Autowired
    private SignInService signInService;


    /**
     * 登陆
     * @param user
     * @return
     */
    @PostMapping("/login")
    @LoginToken
    public Object login(@RequestBody SignIn user) {

        JSONObject jsonObject = new JSONObject();
        SignIn in = signInService.querySignInByName(user);
        if (in == null) {
            jsonObject.put("message", "登录失败,用户不存在");
            return jsonObject;
        } else {
            if (!in.getPassword().equals(user.getPassword())) {
                jsonObject.put("message", "登录失败,密码错误");
                return jsonObject;
            } else {
                String token = JwtTokenUtil.createJWT(6000000, in);
                jsonObject.put("token", token);
                jsonObject.put("user", in);
                return jsonObject;
            }
        }
    }

    /**
     * 查看个人信息
     */
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token标记", required = true) })
    @CheckToken
    @GetMapping("/getMessage")
    public String getMessage() {
        return "你已通过验证";
    }

}
