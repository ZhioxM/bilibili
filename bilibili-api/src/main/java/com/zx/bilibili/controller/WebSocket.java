package com.zx.bilibili.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaFoxUtil;
import cn.hutool.core.util.ObjUtil;
import org.apache.catalina.core.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Mzx
 * @Date: 2023/1/1 18:21
 */
@Component
@ServerEndpoint("/ws/{satoken}")
public class WebSocket {

    private String userId;

    private final Logger logger = LoggerFactory.getLogger(WebSocket.class);

    /**
     * 连接池; TODO 使用sessionID作为key还是userId作为key? 如果是用userId作为key的话，就不能实现多端登录同时推送了；如果使用sessionId作为key的话，在线人数不准确
     */
    private static final Map<String, Session> SESSION_POOL = new ConcurrentHashMap<>();

    private static ApplicationContext applicationContext;

    /**
     * 链接成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "satoken") String satoken) {
        try {
            Object loginId = StpUtil.getLoginIdByToken(satoken);
            if (ObjUtil.isNull(loginId)) {
                session.close(); // 会触发onclose方法
                logger.error("token无效");
                return;
            }
            this.userId = SaFoxUtil.getValueByType(loginId, String.class);
            SESSION_POOL.put(userId, session); // 后来的Session会覆盖
            logger.info("【websocket消息】有新的连接，总数为:" + SESSION_POOL.size());
            sendOneMessage(userId, "WebSocket连接成功, 用户ID: " + userId);
        } catch (Exception ignore) {
        }
    }

    /**
     * 发送错误时的处理
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("用户错误,原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void closeConnection() {
        if (userId != null) {
            SESSION_POOL.remove(userId);
            logger.info("用户退出：" + userId + "当前在线人数为：" + SESSION_POOL.size());
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        logger.info("用户信息：" + userId + "，报文：" + message);
        // TODO 千万级消息推送系统的开发
        // message是JSON字符串
    }

    // 此为广播消息
    public void sendAllMessage(String message) {
        logger.info("【websocket消息】广播消息:" + message);
        for (Session session : SESSION_POOL.values()) {
            try {
                if (session.isOpen()) {
                    session.getAsyncRemote().sendText(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息
    public void sendOneMessage(String userId, String message) {
        Session session = SESSION_POOL.get(userId);
        if (session != null && session.isOpen()) {
            try {
                logger.info("【websocket消息】 单点消息:" + message);
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息(多人)
    public void sendMoreMessage(String[] userIds, String message) {
        for (String userId : userIds) {
            Session session = SESSION_POOL.get(userId);
            if (session != null && session.isOpen()) {
                try {
                    logger.info("【websocket消息】 单点消息:" + message);
                    session.getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
