package com.bling.dab.common.aspect;

import com.bling.dab.common.config.DBContextHolder;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author: hxp
 * @date: 2019/6/14 14:06
 * @description:
 */
@Aspect
@Component
public class DataSourceAspect {

    @Pointcut("!@annotation(com.bling.dab.common.annotation.Master) " +
            "&& (execution(* com.bling.dab.service..*.select*(..)) " +
            "|| execution(* com.bling.dab.service..*.get*(..)))"+
            "|| execution(* com.bling.dab.service..*.find*(..)))"+
            "|| execution(* com.bling.dab.service..*.query*(..)))")
    public void readPointcut() {

    }

    @Pointcut("@annotation(com.bling.dab.common.annotation.Master) " +
            "|| execution(* com.bling.dab.service..*.insert*(..)) " +
            "|| execution(* com.bling.dab.service..*.add*(..)) " +
            "|| execution(* com.bling.dab.service..*.save*(..)) " +
            "|| execution(* com.bling.dab.service..*.update*(..)) " +
            "|| execution(* com.bling.dab.service..*.edit*(..)) " +
            "|| execution(* com.bling.dab.service..*.delete*(..)) " +
            "|| execution(* com.bling.dab.service..*.remove*(..))")
    public void writePointcut() {

    }

    @Before("readPointcut()")
    public void read() {
        DBContextHolder.slave();
    }

    @Before("writePointcut()")
    public void write() {
        DBContextHolder.master();
    }
}
