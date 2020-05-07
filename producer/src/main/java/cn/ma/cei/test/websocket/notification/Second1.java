package cn.ma.cei.test.websocket.notification;

import cn.ma.cei.service.WebSocketNotification;

public class Second1 extends WebSocketNotification {
    private static final String Message1 = "{\"ch\":\"Second1\",\"Name\":\"1\",\"Number\":1,\"Price\":1.1,\"Status\":true}";

    @Override
    public void trigger() {
        getClients().forEachValue(1, client -> {
            client.send(Message1);
        });
    }
}
