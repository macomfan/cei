package cn.ma.cei.test.websocket.processor;

import cn.ma.cei.service.WebSocketClient;
import cn.ma.cei.service.WebSocketMessage;
import cn.ma.cei.service.WebSocketMessageProcessor;
import cn.ma.cei.test.websocket.messages.SubscribeMessage;

public class SubscribeProcessor extends WebSocketMessageProcessor {
    public static final String Success = "{\"op\":\"sub\",\"name\":\"%s\",\"status\":\"ok\"}";
    public static final String Error = "{\"op\":\"sub\",\"name\":\"%s\",\"status\":\"error\"}";

    @Override
    public <T extends WebSocketMessage> void process(T message, WebSocketClient client) {
        SubscribeMessage subscribeMessage = (SubscribeMessage) message;

        if (client.registerNotification(subscribeMessage.name)) {
            client.send(String.format(Success, subscribeMessage.name));
        } else {
            client.send(String.format(Error, subscribeMessage.name));
        }
    }

}
