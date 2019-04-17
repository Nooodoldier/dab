package com.bling.dab.aspect;

import com.alibaba.fastjson.JSON;
import com.bling.dab.domain.Log;
import com.bling.dab.service.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.Future;

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

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

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
        Signature signature = proceedingJoinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();
        //方法名
        String methodName = targetMethod.getName();
        //类名
        String className = methodSignature.getDeclaringType().getName();
        Object proceed = null;
        long millis = 0L;
        Log log = new Log();
        logger.info("调用"+methodName+"入参"+JSON.toJSONString(args));
        sw.start("开始调用"+methodName);
        com.bling.dab.common.annotation.Log annotation = targetMethod.getAnnotation(com.bling.dab.common.annotation.Log.class);
        String action = annotation.action();
        String description = annotation.description();
        String modelName = annotation.modelName();
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        log.setModelName(modelName);
        log.setDescription(description);
        log.setAction(action);
        log.setClassName(className);
        log.setCreateTime(new Date());
        log.setMethodArgs(JSON.toJSONString(args));
        log.setMethodName(methodName);
        log.setUserId(1);
        log.setIp(getIpAddress(request));
        try {
            proceed = proceedingJoinPoint.proceed();
            log.setSuccess("true");
            log.setMessage("执行通过");
        } catch (Throwable throwable) {
            log.setSuccess("false");
            log.setMessage("执行异常"+throwable.getMessage());
            logger.error("调用"+methodName+"异常",throwable);
        }
        sw.stop();
        millis = sw.getLastTaskTimeMillis();
        logger.info("调用"+methodName+"结果返回值为"+JSON.toJSONString(proceed)+",耗时"+millis);
        System.out.print(Thread.currentThread().getId());
        logger.info("线程"+Thread.currentThread().getId());
        //saveLog(log);
        saveLogs(log);
        return proceed;
    }


//    public void saveLog(Log log){
//        try {
//            int i = logService.saveLog(log);
//            logger.info("保存日志记录结果"+(i>0));
//        } catch (Exception e) {
//            logger.error("保存日志记录异常",e);
//        }
//    }

    public void saveLogs(Log log){
        try {
            sw.start();
            Future<String> future = logService.saveLogs(log);
            sw.stop();
            logger.info("保存日志记录结果"+future.isDone()+",耗时"+sw.getLastTaskTimeMillis());
        } catch (Exception e) {
            logger.error("保存日志记录异常",e);
        }
    }


//    public void saveLog(Log log){
//        try {
//            threadPoolTaskExecutor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    logger.info("线程"+Thread.currentThread().getId());
//                    int i = logService.saveLog(log);
//                    logger.info("保存日志记录结果"+(i>0));
//                }
//            });
//
//        } catch (Exception e) {
//            logger.error("保存日志记录异常",e);
//        }
//    }


    private  String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip+":"+request.getRemotePort();
    }
}
