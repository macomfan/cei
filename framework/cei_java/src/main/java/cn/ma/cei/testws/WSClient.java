package cn.ma.cei.testws;

import cn.ma.cei.impl.*;

public class WSClient extends WebSocketConnection {
    public void connect() {
        WebSocketEvent event = new WebSocketEvent();
        event.setTrigger((msg) -> {
            return true;
        });
        event.setEvent((msg)->{
            send(new JsonWrapper());
        });

        WebSocketOption option = new WebSocketOption();
        connect(option, () -> {
            send(new JsonWrapper());
        });
    }
}
