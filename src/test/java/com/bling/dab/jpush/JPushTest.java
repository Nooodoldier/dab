package com.bling.dab.jpush;

import cn.jpush.api.push.PushResult;
import com.alibaba.fastjson.JSON;
import com.bling.dab.common.util.JPushUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: hxp
 * @date: 2019/4/15 14:02
 * @description:
 */
public class JPushTest {

    protected static final Logger LOG = LoggerFactory.getLogger(JPushTest.class);

    @Test
    public void JPushMessage(){
        PushResult result = JPushUtil.pushMessage();
        LOG.info("极光推送消息结果"+ JSON.toJSONString(result));
    }
}
