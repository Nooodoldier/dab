package com.bling.dab.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.bling.dab.common.annotation.Auth;
import com.bling.dab.common.config.RedisConfig;
import com.bling.dab.common.util.RedisUtil;
import com.bling.dab.domain.SessionData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

import static com.bling.dab.common.constant.Constants.MOBILE_NUMBER_SESSION_KEY;
import static com.bling.dab.common.constant.Constants.SESSION_KEY;
import static com.bling.dab.common.constant.Constants.USER_CODE_SESSION_KEY;

/**
 * @author: hxp
 * @date: 2019/4/24 20:03
 * @description:
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private RedisConfig redisConfig;

    private final static String SESSION_KEY_PREFIX = "session:";
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            return true;
        }
        handlerSession(request);

        final HandlerMethod handlerMethod = (HandlerMethod) handler;
        final Method method = handlerMethod.getMethod();
        final Class<?> clazz = method.getDeclaringClass();
        if (clazz.isAnnotationPresent(Auth.class) ||
                method.isAnnotationPresent(Auth.class)) {
            if(request.getAttribute(USER_CODE_SESSION_KEY) == null){

                throw new Exception();

            }else{
                return true;
            }
        }

        return true;

    }
    public void  handlerSession(HttpServletRequest request) {
        RedisUtil redisUtil = RedisUtil.getInstance(redisConfig);
        String sessionId = request.getHeader(SESSION_KEY);
        if(StringUtils.isBlank(sessionId)){
            sessionId=(String) request.getSession().getAttribute(SESSION_KEY);
        }
        if (StringUtils.isNotBlank(sessionId)) {
            SessionData model = JSONObject.parseObject(redisUtil.get(SESSION_KEY_PREFIX + sessionId), SessionData.class);
            if (model == null) {
                return ;
            }
            request.setAttribute(SESSION_KEY,sessionId);
            Integer userCode = model.getUserCode();
            if (userCode != null) {
                request.setAttribute(USER_CODE_SESSION_KEY, Long.valueOf(userCode));
            }
            String mobile = model.getMobileNumber();
            if (mobile != null) {
                request.setAttribute(MOBILE_NUMBER_SESSION_KEY, mobile);
            }
        }
        return ;
    }
    
}
