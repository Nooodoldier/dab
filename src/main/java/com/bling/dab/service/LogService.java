package com.bling.dab.service;

import com.bling.dab.mapper.LogMapper;
import com.bling.dab.domain.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * @author: hxp
 * @date: 2019/4/15 20:12
 * @description:
 */
@Service
public class LogService {

    private final static Logger logger = LoggerFactory.getLogger(LogService.class);
    @Autowired
    private LogMapper logMapper;


    public int saveLog(Log log){
        return logMapper.saveLog(log);
    }

    @Async(value = "taskExecutor")
    public Future<String> saveLogs(Log log){
        logger.info("线程"+Thread.currentThread().getId());
        AsyncResult result = null;
        try {

            logger.info("保存日志结果"+(saveLog(log)>0));
            result = new AsyncResult<>("执行成功");
        } catch (Exception e) {
            result = new AsyncResult<>("执行异常"+e.getMessage());
        }
        return result;
    }

}
