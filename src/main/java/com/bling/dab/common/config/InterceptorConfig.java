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
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**","/images/**","/js/**","/login.html","/services/*");
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**","/images/**","/js/**","/login.html","/services/*");
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
    @Bean
    public ViewResolver viewResolver() {
        logger.info("ViewResolver");
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}
