package com.example.websocketdemo3.common;/**
 * @projectName: websocketdemo3
 * @package: com.example.websocketdemo3.common
 * @className: WebSocketGroup
 * @author: mlqj
 * @description:
 */

import jakarta.websocket.Session;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *@Author: zdh
 *@CreateTime: 2025-03-16
 *@Description:
 *@Version: 0.1
 */
public class WebSocketGroup {
    private static final Logger log = LoggerFactory.getLogger(WebSocketGroup.class);
    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);
    private static final Map<String, Session> ONLINE_SESSIONS = new ConcurrentHashMap<>();
    static final DateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void sendAll(String message, Session session){
        message = "[游客："+session.getId()+"]["+DATEFORMAT.format(new Date())+"]"+message;
        // 遍历时使用 ConcurrentHashMap 的线程安全特性
        for (Map.Entry<String, Session> entry : ONLINE_SESSIONS.entrySet()) {
            Session one = entry.getValue();
            if (one.isOpen()) {
                one.getAsyncRemote().sendText(message, result -> {
                    if (!result.isOK()) {
                        log.error("{} 客户端发送消息失败", entry.getKey(), result.getException());
                    }
                });
            }
        }


    }

    public static void addSession(Session session){
        synchronized (ONLINE_SESSIONS) {
            ONLINE_SESSIONS.put(session.getId(), session);
            ONLINE_COUNT.incrementAndGet();
        }
        WebSocketGroup.sendAll("进入聊天室",session);
        log.info("有新连接加入，id:{},当前在线人数为：{}",session.getId(),ONLINE_COUNT.get());
    }

    public static void removeSession(Session session){
        synchronized (ONLINE_SESSIONS) {
            ONLINE_SESSIONS.remove(session.getId());
            ONLINE_COUNT.decrementAndGet();
        }
        WebSocketGroup.sendAll("离开聊天室",session);
        log.info("id{}连接关闭，当前在线人数为：{}",session.getId(),ONLINE_COUNT.get());
    }
}
