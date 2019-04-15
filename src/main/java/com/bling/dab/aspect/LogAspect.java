package com.bling.dab.aspect;

import com.bling.dab.common.annotation.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.SourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.annotation.Annotation;

/**
 * @author: hxp
 * @date: 2019/4/12 15:44
 * @description:
 */
@Aspect
@Component
public class LogAspect {

    private final static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    private final static StopWatch sw = new StopWatch();

    @Pointcut("@annotation(com.bling.dab.common.annotation.Log)")
    public void log(){

    }

    @Around("log()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint){
        Object[] args = proceedingJoinPoint.getArgs();
        String name = proceedingJoinPoint.getSignature().getName();
        Object proceed = null;
        try {
            proceed = proceedingJoinPoint.proceed();
            logger.info("");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            logger.error("",throwable);
        }
        return proceed;
    }
}
