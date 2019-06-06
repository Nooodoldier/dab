package com.bling.dab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bling.dab.domain.Log;

/**
 * @author: hxp
 * @date: 2019/4/15 20:13
 * @description:
 */
public interface LogMapper extends BaseMapper<Log> {

    int saveLog(Log log);
}
