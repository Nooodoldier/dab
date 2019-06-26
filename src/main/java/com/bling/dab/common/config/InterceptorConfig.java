package com.bling.dab.common.config;


import com.bling.dab.common.interceptor.AuthenticationInterceptor;
import com.bling.dab.common.interceptor.LoginInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author: hxp
 * @date: 2019/5/10 15:37
 * @description:
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private static final Logger logger =  LoggerFactory.getLogger(InterceptorConfig.class);

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;
    @Autowired
    private LoginInterceptor loginInterceptor;

    /**
     * 需要登陆状态的接口路径在此拦截
     * 对外提供的API不需要拦截,需要添加特定的令牌校验，暂时没有，例如/services/*
     * 不需要登陆状态的请求路径或测试的路径可以在拦截器排除配置且在shiro中配置不拦截
     * authenticationInterceptor即使不加注解返回也是true，只是通过checkToken选择性的校验token,不加不校验，
     * 但shiro会拦截所有路径，所以需要配置不校验的路径
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**","/login.html","/services/**");
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**","/login.html","/services/**");
        // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录或者通过excludePathPatterns配置不需要拦截的路径
        //多拦截器配置
    }

    /**
     * <p>
     *     视图处理器也可以在配置文件配置
     * </p>
     *
     * @return
     */
//    @Bean
//    public ViewResolver viewResolver() {
//        logger.info("ViewResolver");
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setPrefix("/WEB-INF/jsp/");
//        viewResolver.setSuffix(".jsp");
//        return viewResolver;
//    }
}
