package com.bling.dab.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;


/**
 * @author: hxp
 * @date: 2019/4/18 14:55
 * @description:spring提供的定时任务默认是单线程,会造成任务执行时间偏移,所以此处用自定义的线程池
 */
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(getExecutor());
    }

    @Bean(destroyMethod = "shutdown")
    public ExecutorService getExecutor() {
        return new ScheduledThreadPoolExecutor(20);
    }

}
