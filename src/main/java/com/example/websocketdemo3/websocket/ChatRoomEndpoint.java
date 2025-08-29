package com.example.websocketdemo3.websocket;/**
 * @projectName: websocketdemo3
 * @package: com.example.websocketdemo3.websocket
 * @className: ChatRoomEndpoint
 * @author: mlqj
 * @description:
 */

import com.example.websocketdemo3.common.WebSocketGroup;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 *@Author: zdh
 *@CreateTime: 2025-03-16
 *@Description:
 *@Version: 0.1
 */
@Component
@ServerEndpoint("/chatroom")
public class ChatRoomEndpoint {
    @OnOpen
    public void onOpen(Session session) throws IOException {
        WebSocketGroup.addSession(session);
        synchronized (session) {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText("--- 您的ID：用户" + session.getId() + " ---");
                } catch (IOException e) {
                    e.printStackTrace();
                    // 发送失败时尝试关闭会话
                    try {
                        session.close(new CloseReason(CloseReason.CloseCodes.CLOSED_ABNORMALLY, "发送欢迎消息失败"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) throws IOException {
        WebSocketGroup.removeSession(session);
    }

    @OnMessage
    public void onMessage(String message,Session session) throws IOException {
        synchronized (session) {
            if (session.isOpen()) {
                WebSocketGroup.sendAll(message, session);
            }
        }
    }

    @OnError
    public void onError(Session session,Throwable error) throws IOException {
        error.printStackTrace();
        if (session.isOpen()) {
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, "发生错误"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
