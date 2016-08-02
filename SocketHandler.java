package cn.thinkjoy.topic.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wanggenwen on 16/7/19.
 */

public abstract class SocketHandler extends TextWebSocketHandler {

    private static final Logger logger;

    private static final String keys[];

    private static final Map<String, List<WebSocketSession>> clients;

    static {
        clients = new ConcurrentHashMap<>();

        keys = new String[]{"key"};

        logger = LoggerFactory.getLogger(SocketHandler.class);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        final String key = SocketUtil.generateKey(session, keys);

        List<WebSocketSession> sessions = clients.get(key);
        if(null == sessions) {
            sessions = new ArrayList<>();
            clients.put(key, sessions);
        }
        sessions.add(session);

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        sendMessageToUsers(message);

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

        String key = SocketUtil.generateKey(session, keys);

        logger.error("websocket connection exception: " + key);

        logger.error(exception.getMessage(), exception);

        List<WebSocketSession> sessions = clients.get(key);

        if(session.isOpen()) {
            session.close();
        }
        if(null != sessions) {
            sessions.remove(session);
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        String key = SocketUtil.generateKey(session, keys);

        List<WebSocketSession> sessions = clients.get(key);

        if(session.isOpen()) {
            session.close();
        }
        if(null != sessions) {
            sessions.remove(session);
        }

    }

    public void sendMessageToUsers(TextMessage message) throws IOException{
        for(String key: clients.keySet()) {
            List<WebSocketSession> temps = clients.get(key);
            if(null == temps) {
                continue;
            }
            for(WebSocketSession session: temps) {
                if(!session.isOpen()) {
                    continue;
                }
                session.sendMessage(message);
            }
        }
    }

}
