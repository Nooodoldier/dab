package com.bling.dab.task;

import com.bling.dab.common.redis.RedisConfig;
import com.bling.dab.common.redis.RedisUtil;
import com.bling.dab.common.util.HostUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: hxp
 * @date: 2019/4/18 11:29
 * @description:spring自带的定时任务
 */
@Component
@EnableScheduling
public class ScheduledTask {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    private String cronKey = "cronKey";

    @Resource
    private RedisConfig redisConfig;

    @Scheduled(fixedRate = 1000 * 30)
    public void reportCurrentTime(){
        System.out.println ("Scheduling Tasks Examples: The time is now " + dateFormat ().format (new Date ()));
    }

    //每1分钟执行一次
    @Scheduled(cron = "0 */1 *  * * * ")
    public void reportCurrentByCron(){

        RedisUtil redisUtil = RedisUtil.getInstance(redisConfig);
        try {
            String hostName = HostUtil.getHostName()+ HostUtil.getIp();
            if (!redisUtil.exists(cronKey)){
                redisUtil.setex(cronKey,hostName,24*60*60);
                logger.info("首次执行："+hostName);
            }else {
                String value = redisUtil.get(cronKey);
                if (!hostName.equals(value)){
                    logger.info("hostName不一致");
                    return;
                }
                redisUtil.setex(cronKey,hostName,24*60*60);
            }
        }catch (Exception e){
            logger.error("redis异常",e);
        }
        /**开始执行*/
        System.out.println ("Scheduling Tasks Examples By Cron: The time is now " + dateFormat ().format (new Date()));
    }

    private SimpleDateFormat dateFormat(){
        return new SimpleDateFormat("HH:mm:ss");
    }
}
