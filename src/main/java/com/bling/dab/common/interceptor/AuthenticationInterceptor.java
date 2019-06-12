package com.bling.dab.common.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.bling.dab.common.annotation.CheckToken;
import com.bling.dab.common.annotation.LoginToken;
import com.bling.dab.common.model.SignInReq;
import com.bling.dab.common.util.JwtTokenUtil;
import com.bling.dab.domain.SignIn;
import com.bling.dab.domain.UserInfo;
import com.bling.dab.service.SignInService;
import com.bling.dab.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;



/**
 * @author: hxp
 * @date: 2019/5/10 15:15
 * @description:
 */
@Slf4j
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {

        // 从 http 请求头中取出 token
        String token = httpServletRequest.getHeader("token");
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();

        CheckToken ct = handlerMethod.getMethodAnnotation(CheckToken.class);
        if(ct!=null){
            log.info("@LoginToken注解不为空！");
        }
        //检查是否有LoginToken注释，有则跳过认证
        if (method.isAnnotationPresent(LoginToken.class)) {
            LoginToken loginToken = method.getAnnotation(LoginToken.class);
            if (loginToken.required()) {
                return true;
            }
        }

        //检查有没有需要用户权限的注解
        boolean present = method.isAnnotationPresent(CheckToken.class);
        if (present) {
            CheckToken checkToken = method.getAnnotation(CheckToken.class);
            if (checkToken.required()) {
                // 执行认证
                if (token == null) {
                    log.info("无token，请重新登录");
                    throw new RuntimeException("无token，请重新登录");
                }
                // 获取 token 中的 user id
                String userId;
                try {
                    userId = JWT.decode(token).getClaim("id").asString();
                } catch (JWTDecodeException j) {
                    log.info("访问异常！");
                    throw new RuntimeException("访问异常！");
                }
                UserInfo info = userInfoService.findUserInfoByUid(Integer.parseInt(userId));
                if (info == null) {
                    log.info("用户不存在，请重新登录");
                    throw new RuntimeException("用户不存在，请重新登录");
                }
                SignInReq sign = new SignInReq();
                sign.setId(Integer.toString(info.getUid()));
                sign.setUserName(info.getUsername());
                sign.setPassword(info.getPassword());
                Boolean verify = JwtTokenUtil.isVerify(token, sign);
                if (!verify) {
                    log.info("非法访问！");
                    throw new RuntimeException("非法访问！");
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }


}
