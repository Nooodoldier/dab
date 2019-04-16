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
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
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
            log.setMessage("执行成功");
        } catch (Throwable throwable) {
            log.setSuccess("false");
            log.setMessage("执行异常"+throwable.getMessage());
            logger.error("调用"+methodName+"异常",throwable);
        }
        sw.stop();
        millis = sw.getLastTaskTimeMillis();
        logger.info("调用"+methodName+"结果返回值为"+JSON.toJSONString(proceed)+",耗时"+millis);
        logService.saveLog(log);
        return proceed;
    }

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
