package com.bling.dab.service;

import com.bling.dab.dao.LogMapper;
import com.bling.dab.domain.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: hxp
 * @date: 2019/4/15 20:12
 * @description:
 */
@Service
public class LogService {

    @Autowired
    private LogMapper logMapper;

    public int saveLog(Log log){
        return logMapper.saveLog(log);
    }
}
