package com.bling.dab.common.util;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.DefaultResult;
import cn.jpush.api.JPushClient;
import cn.jpush.api.device.TagAliasResult;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.*;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.report.ReceivedsResult;
import cn.jpush.api.schedule.ScheduleResult;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: hxp
 * @date: 2019/4/15 11:50
 * @description:极光推送
 */
public class JPushUtil {

    protected static final Logger LOG = LoggerFactory.getLogger(JPushUtil.class);

    private static final String MASTER_SECRET = "56f57e3fab87ce5a1a9d9757";

    private static final String APP_KEY = "a6df5cba0f3e283c81cdfa5a";

    /**
     * 推送
     * @return
     */
    public static PushResult pushMessage(){

        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());
        //PushPayload payload = buildPushObject_all_all_alert("下午好！xxx11");
        PushPayload payload = buildPushObject_all_alias_alert("下午好！xxx11");
       // PushPayload payload = buildPushObject_android_tag_alertWithTitle("下午好！xxx");
       // PushPayload payload = buildPushObject_ios_tagAnd_alertWithExtrasAndMessage("下午好！xxx");
        //PushPayload payload = buildPushObject_ios_audienceMore_messageWithExtras();
        PushResult result = null;
        try {

            result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + JSON.toJSONString(result));

        } catch (APIConnectionException e) {
            LOG.error("Connection error, should retry later", e);

        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            LOG.error("Should review the error, and fix the request", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
        return result;
    }

    /**
     * 快捷地构建推送对象：所有平台，所有设备，内容为 ALERT 的通知
     * @return
     */
    public static PushPayload buildPushObject_all_all_alert(String alert) {
        return PushPayload.alertAll(alert);
    }


    /**
     * 构建推送对象：所有平台，推送目标是别名为 "alias1"，通知内容为 ALERT。
     * @return
     */
    public static PushPayload buildPushObject_all_alias_alert(String alert) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias("rrrr"))
                .setNotification(Notification.alert(alert))
                .build();
    }
    /**
     * 构建推送对象：平台是 Android，目标是 tag 为 "tag1" 的设备，内容是 Android 通知 ALERT，并且标题为 TITLE。
     */
    public static PushPayload buildPushObject_android_tag_alertWithTitle(String alert) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag("tag1"))
                .setNotification(Notification.android(alert, "TITLE", null))
                .build();
    }


    /**
     * 构建推送对象：平台是 iOS，推送目标是 "tag1", "tag_all" 的交集，推送内容同时包括通知与消息 -
     * 通知信息是 ALERT，角标数字为 5，通知声音为 "happy"，并且附加字段 from = "JPush"；消息内容
     * 是 MSG_CONTENT。通知是 APNs 推送通道的，消息是 JPush 应用内消息通道的。APNs 的推送环境是
     * “生产”（如果不显式设置的话，Library 会默认指定为开发）
     * @return
     */
    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage(String alert) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.tag_and("tag1", "tag_all"))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(alert)
                                .setBadge(5)
                                .setSound("happy")
                                .addExtra("from", "JPush")
                                .build())
                        .build())
                .setMessage(Message.content("1001"))
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)
                        .build())
                .build();
    }


    /**
     * 构建推送对象：平台是 Andorid 与 iOS，推送目标是 （"tag1" 与 "tag2" 的并集）交（"alias1" 与 "alias2" 的并集），
     * 推送内容是 -内容为 MSG_CONTENT 的消息，并且附加字段 from = JPush。
     * @return
     */
    public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
                        .addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent("111")
                        .addExtra("from", "JPush")
                        .build())
                .build();
    }


    /**
     * 构建推送对象：推送内容包含SMS信息
     */
    public static void sendAndroidMessageWithAlias() {
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        try {
            SMS sms = SMS.newBuilder()
                    .setDelayTime(1000)
                    .setTempID(2000)
                    .addPara("Test", 1)
                    .build();
            PushResult result = jpushClient.sendAndroidMessageWithAlias("Test SMS", "test sms", sms, "alias1");
            LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }



    /**
     * 统计推送
     * @return
     */
    public static ReceivedsResult getReportReceiveds(){

        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        ReceivedsResult result = null;
        try {
            result = jpushClient.getReportReceiveds("1942377665");
            LOG.debug("Got result - " + result);

        } catch (APIConnectionException e) {
            // Connection error, should retry later
            LOG.error("Connection error, should retry later", e);

        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            LOG.error("Should review the error, and fix the request", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
        return result;
    }


    /**
     * 获取Tag Alias
     */
    public static TagAliasResult getTagAlias(){
        TagAliasResult result = null;
        try {
            JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
            //REGISTRATION_ID1
            result = jpushClient.getDeviceTagAlias("");

            LOG.info(result.alias);
            LOG.info(result.tags.toString());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
        return result;
    }

    /**
     * 绑定手机号
     * @return
     */
    public static DefaultResult bindMobile(){
        DefaultResult result = null;
        try {
            JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
            //REGISTRATION_ID1
            result =  jpushClient.bindMobile("", "");
            LOG.info("Got result " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
        return result;
    }

    /**
     * Schedule
     * @return
     */
    public static ScheduleResult createSingleSchedule(){
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        String name = "test_schedule_example";
        String time = "2016-07-30 12:30:25";
        PushPayload push = PushPayload.alertAll("");
        ScheduleResult result = null;
        try {
            result = jpushClient.createSingleSchedule(name, time, push);
            LOG.info("schedule result is " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
        return result;
    }

}
