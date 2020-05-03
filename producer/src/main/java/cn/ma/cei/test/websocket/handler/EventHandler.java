package cn.ma.cei.test.websocket.handler;

import cn.ma.cei.service.IWebSocketHandler;
import cn.ma.cei.service.WebSocketClient;
import cn.ma.cei.service.WebSocketService;
import cn.ma.cei.test.websocket.messages.EchoMessage;
import cn.ma.cei.test.websocket.messages.SubscribeMessage;
import cn.ma.cei.test.websocket.processor.EchoProcessor;
import cn.ma.cei.test.websocket.processor.SubscribeProcessor;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

public class EventHandler implements IWebSocketHandler {
    private static final String ErrorMessageType = "{\"op\":\"error\",\"msg\":\"Unknown message type\"}";
    private static final String ErrorMessageFormat = "{\"op\":\"error\",\"msg\":\"Error message format\"}";

    private static final EchoProcessor ECHO_PROCESSOR = new EchoProcessor();
    private static final SubscribeProcessor SUBSCRIBE_PROCESSOR = new SubscribeProcessor();

    @Override
    public WebSocketService.MessageResult handle(String text, Buffer buffer, WebSocketClient client) {
        System.err.println("Receive request " + text);
        String op;
        JsonObject jsonObject;
        try {
            jsonObject = new JsonObject(text);
            op = jsonObject.getString("op");
        } catch (Exception e) {
            client.send(ErrorMessageFormat);
            return WebSocketService.MessageResult.ignore();
        }
        switch (op) {
            case "echo": {
                EchoMessage echoMessage = new EchoMessage();
                echoMessage.text = text;
                return WebSocketService.MessageResult.normal(echoMessage, ECHO_PROCESSOR);
            }
            case "sub": {
                SubscribeMessage subscribeMessage = new SubscribeMessage();
                subscribeMessage.name = jsonObject.getString("name");
                return WebSocketService.MessageResult.normal(subscribeMessage, SUBSCRIBE_PROCESSOR);
            }
            case "pong": {
                return WebSocketService.MessageResult.ignore();
            }
            default: {
                client.send(ErrorMessageType);
                return WebSocketService.MessageResult.ignore();
            }
        }
    }
}
