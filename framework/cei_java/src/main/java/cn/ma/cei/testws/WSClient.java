package cn.ma.cei.testws;

import cn.ma.cei.impl.*;

public class WSClient extends WebSocketConnection {
    public void connect() {
        WebSocketAction event = new WebSocketAction();
        event.setTrigger((msg) -> {
            return true;
        });
        event.setEvent((msg)->{
            send(new JsonWrapper());
        });

        WebSocketOptions option = new WebSocketOptions();
        connect(option, () -> {
            send(new JsonWrapper());
        });
    }
}
