package cn.thinkjoy.topic.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created by wanggenwen on 16/7/19.
 */
@Configuration
@EnableWebSocket//开启websocket
public abstract class SocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(socketHandler(), "/websocket").addInterceptors(new SocketInterceptor()).setAllowedOrigins(
                "http://localhost:8080", "http://127.0.0.1:8080");

    }

    @Bean
    public SocketHandler socketHandler() {
        return null;
    }
}
