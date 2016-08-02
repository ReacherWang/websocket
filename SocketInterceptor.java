package cn.thinkjoy.topic.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by wanggenwen on 16/7/19.
 */
public class SocketInterceptor extends HttpSessionHandshakeInterceptor {

    private static final Logger logger;

    static {
        logger = LoggerFactory.getLogger(SocketInterceptor.class);
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        logger.debug("before handshake");

        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            HttpServletRequest request = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();
            attributes.put("key", request.getParameter("key"));
        }

        return super.beforeHandshake(serverHttpRequest, serverHttpResponse, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

        logger.debug("after handshake");

        super.afterHandshake(request, response, wsHandler, exception);
    }
}
