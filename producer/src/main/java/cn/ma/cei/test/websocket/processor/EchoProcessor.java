package cn.ma.cei.test.websocket.processor;

import cn.ma.cei.service.WebSocketClient;
import cn.ma.cei.service.WebSocketMessage;
import cn.ma.cei.service.WebSocketMessageProcessor;
import cn.ma.cei.test.websocket.messages.EchoMessage;

public class EchoProcessor extends WebSocketMessageProcessor {

    @Override
    public <T extends WebSocketMessage> void process(T message, WebSocketClient client) {
        EchoMessage echoMessage = (EchoMessage)message;
        client.send(echoMessage.text);
    }
}
