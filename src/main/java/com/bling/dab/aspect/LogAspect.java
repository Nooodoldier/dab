package com.bling.dab.aspect;

import com.alibaba.fastjson.JSON;
import com.bling.dab.common.util.DateUtil;
import com.bling.dab.domain.Log;
import com.bling.dab.service.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.annotation.Annotation;
import java.util.Date;

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

    @Autowired
    private LogService logService;

    @Pointcut("@annotation(com.bling.dab.common.annotation.Log)")
    public void log(){

    }

    /**
     * 日志记录
     * @param proceedingJoinPoint
     * @return
     */
    @Around("log()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint){
        Object[] args = proceedingJoinPoint.getArgs();
        String methodName = proceedingJoinPoint.getSignature().getName();
        String className = proceedingJoinPoint.getClass().getName();
        Object proceed = null;
        long millis = 0L;
        try {
            logger.info("调用"+methodName+"入参"+JSON.toJSONString(args));
            sw.start("开始调用"+methodName);
            proceed = proceedingJoinPoint.proceed();
            sw.stop();
            millis = sw.getLastTaskTimeMillis();
            Log log = new Log();
            com.bling.dab.common.annotation.Log[] annotations = proceedingJoinPoint.getClass().getAnnotationsByType(com.bling.dab.common.annotation.Log.class);
            String action = annotations[0].action();
            String description = annotations[0].description();
            String modelName = annotations[0].modelName();
            log.setModelName(modelName);
            log.setDescription(description);
            log.setAction(action);
            log.setClassName(className);
            log.setCreateTime(new Date());
            log.setMethodArgs(JSON.toJSONString(args));
            log.setSuccess("true");
            log.setUserId(1);
            log.setIp("");
            log.setMessage("cg");
            int i = logService.saveLog(log);
            logger.info("调用"+methodName+"结果"+(i>0)+",返回值为"+JSON.toJSONString(proceed)+",耗时"+millis);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            logger.error("调用"+methodName+"异常",throwable);
        }
        return proceed;
    }
}
