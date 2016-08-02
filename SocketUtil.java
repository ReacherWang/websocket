package cn.thinkjoy.topic.websocket;

import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

/**
 * Created by wanggenwen on 16/7/20.
 */
public final class SocketUtil {

    public static final String generateKey(WebSocketSession session, String ...keys) {

        if(null == keys || 0 == keys.length) {
            return session.getId();
        }
        Map<String, Object> attributes = session.getAttributes();
        StringBuffer result = new StringBuffer();
        for(int i = 0; i < keys.length; ++i) {
            if(0 != i) {
                result.append("-");
            }
            result.append(String.valueOf(attributes.get(keys[i])));
        }
        return result.toString();
    }


}
