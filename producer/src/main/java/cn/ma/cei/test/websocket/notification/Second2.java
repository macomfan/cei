package cn.ma.cei.test.websocket.notification;

import cn.ma.cei.service.WebSocketNotification;

public class Second2 extends WebSocketNotification {
    private static final String Message2 = "{\"ch\":\"Second2\",\"Name\":\"2\",\"Number\":2,\"Price\":2.2,\"Status\":false}";

    @Override
    public void trigger() {
        getClients().forEachValue(1, client -> {
            client.send(Message2);
        });
    }
}
