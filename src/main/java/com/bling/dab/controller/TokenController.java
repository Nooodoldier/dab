package com.bling.dab.controller;

import com.alibaba.fastjson.JSONObject;
import com.bling.dab.common.annotation.CheckToken;
import com.bling.dab.common.annotation.LoginToken;
import com.bling.dab.common.model.SignInReq;
import com.bling.dab.common.util.DateUtil;
import com.bling.dab.common.util.EncryptUtil;
import com.bling.dab.common.util.JwtTokenUtil;
import com.bling.dab.domain.UserInfo;
import com.bling.dab.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
    private UserInfoService userInfoService;
    /**   令牌过期时间设置为1天=毫秒*秒*分*时   */
    private static final long expireTime = 1000*60*60*24L;

    /**
     * 1.当用户登录成功后，签发了一个token，设置了过期时间，假设为2个小时;
     *
     * 2.当用户距离到期时间大于30分钟的时候，用户携带原token访问接口，此时不签发新token，原token依然有效;
     *
     * 3.当用户距离到期时间小于30分钟，但是并没有过期，用户携带原token访问接口，这个时候签发新token，原token失效;
     *
     * 4.当用户token已经过期，直接签发新token。
     */
    /**
     * 登陆获取token
     * @param username
     * @param password
     * @return
     */
    @ApiOperation("登陆获取token")
    @PostMapping("/login")
    @LoginToken
    public Object login(@RequestParam("username") String username, @RequestParam("password") String password) {

        JSONObject jsonObject = new JSONObject();
        UserInfo userInfo = userInfoService.findByUsername(username);
        if (userInfo == null) {
            jsonObject.put("message", "登录失败,用户不存在");
            return jsonObject;
        } else {
            EncryptUtil encryptUtil = EncryptUtil.getInstance();
            String passcode = encryptUtil.MD5(encryptUtil.Base64Encode(password) + userInfo.getSalt());
            //校验登陆密码加盐的一致性
            if (!userInfo.getPassword().equals(passcode)) {
                jsonObject.put("message", "登录失败,密码错误");
                return jsonObject;
            } else {
                //shiro的token
                //添加用户认证信息
                Subject subject = SecurityUtils.getSubject();
                UsernamePasswordToken shiroToken = new UsernamePasswordToken(userInfo.getUsername(),userInfo.getPassword());
                //进行验证，这里可以捕获异常，然后返回对应信息
                subject.login(shiroToken);

                SignInReq sign = new SignInReq();
                sign.setId(Integer.toString(userInfo.getUid()));
                sign.setUserName(userInfo.getUsername());
                //此处的密码是加盐加密过后的
                sign.setPassword(userInfo.getPassword());
                long now = System.currentTimeMillis();
                String dateStr = DateUtil.parseDateToStr(new Date(now+expireTime), DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
                String token = JwtTokenUtil.createJWT(expireTime, sign);
                jsonObject.put("token", token);
                jsonObject.put("expireTime",dateStr);
                jsonObject.put("sign", sign);
                return jsonObject;
            }
        }
    }

    /**
     * 查看个人信息
     * swagger传参需要此注解传token,postman不需要
     */
    @ApiOperation("查看个人信息-验证token有效性")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token标记", required = true) })
    @CheckToken
    @GetMapping("/getMessage")
    public String getMessage() {
        return "你已通过验证";
    }

}
