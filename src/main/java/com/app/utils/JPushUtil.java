package com.app.utils;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.ClientConfig;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/2.
 */
public class JPushUtil {
    protected static final Logger LOG = LoggerFactory.getLogger(JPushUtil.class);

    private static final String appKey ="6b7a64ec0c17f0dadbd4a003";
    private static final String masterSecret = "ff5c9f20d20495ef174742a7";
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass().getName());
    static ClientConfig clientConfig = ClientConfig.getInstance();
    static JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, clientConfig);

    //推送给所有用户,参数分别是：标题，推送内容，具体实体json
    public static void pushToAll(String title, String alertContent,String json) {
        // HttpProxy proxy = new HttpProxy("localhost", 3128);
        // Can use this https proxy: https://github.com/Exa-Networks/exaproxy
        // For push, all you need do is to build PushPayload object.

        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.all())
                .setNotification(
                    Notification.newBuilder()
                            .addPlatformNotification(AndroidNotification.newBuilder()
                                    .setAlert(alertContent)
                                    .setTitle(title)
                                    .addExtra("detail", json)
                                    .build())
                            .build()
                )
                .build();
        try {
            jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            new JPushUtil().logger.error(ExceptionUtil.getStackTrace(e));
        } catch (APIRequestException e) {
            new JPushUtil().logger.error(ExceptionUtil.getStackTrace(e));
        }
    }

    //推送给指定人群，参数分别是：标题，推送内容，人群标识，具体json数据
    public static void pushToGroup(String title, String alertContent, String tag, String json){
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag(tag))
                .setNotification(
                    Notification.newBuilder()
                            .addPlatformNotification(AndroidNotification.newBuilder()
                                    .setAlert(alertContent)
                                    .setTitle(title)
                                    .addExtra("detail", json)
                                    .build())
                            .build()
                )
                .build();
        try {
            jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            new JPushUtil().logger.error(ExceptionUtil.getStackTrace(e));
        } catch (APIRequestException e) {
            new JPushUtil().logger.error(ExceptionUtil.getStackTrace(e));
        }
    }

    //推动给指定用户,四个参数分别为提醒的标题，内容，指定用户标识，具体json数据
    public static void pushToOne(String title,String alertContent, String alias,String json) {

        PushPayload payload =  PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(alias))
                .setNotification(
                    Notification.newBuilder()
                            .addPlatformNotification(AndroidNotification.newBuilder()
                                    .setAlert(alertContent)
                                    .setTitle(title)
                                    .addExtra("detail", json)
                                    .build())
                            .build()
                )
                .build();
        try {
            jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            new JPushUtil().logger.error(ExceptionUtil.getStackTrace(e));
        } catch (APIRequestException e) {
            new JPushUtil().logger.error(ExceptionUtil.getStackTrace(e));
        }
    }

    public static void main(String[] args) {
        Map<String,String> map = new HashMap<String, String>();
        map.put("type","shequ");
        pushToOne("自定义提醒","XXX回复了你的XXX","1471093688543",null);
    }
}
