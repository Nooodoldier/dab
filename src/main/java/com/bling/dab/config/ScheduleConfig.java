package com.bling.dab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;


/**
 * @author: hxp
 * @date: 2019/4/18 14:55
 * @description:spring提供的定时任务默认是单线程,会造成任务执行时间偏移,所以此处用自定义的线程池
 */
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(taskExecutor);
    }
}
