package cn.ma.cei.test.websocket.request;

import cn.ma.cei.generator.buildin.WebSocketConnection;
import cn.ma.cei.service.IWebSocketHandler;
import cn.ma.cei.service.WebSocketMessage;
import cn.ma.cei.service.WebSocketService;
import cn.ma.cei.test.websocket.messages.EchoMessage;
import io.vertx.core.buffer.Buffer;

public class RequestHandler implements IWebSocketHandler {
    @Override
    public WebSocketService.MessageResult handle(String text, Buffer buffer) {
        if ("Echo".equals(text)) {
            EchoMessage echoMessage = new EchoMessage();
            echoMessage.text = text;
            return WebSocketService.MessageResult.normal(echoMessage);
        }
        return WebSocketService.MessageResult.error();
    }
}
