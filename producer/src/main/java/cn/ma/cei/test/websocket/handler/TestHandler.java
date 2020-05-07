package cn.ma.cei.test.websocket.handler;

import cn.ma.cei.service.IWebSocketHandler;
import cn.ma.cei.service.WebSocketClient;
import cn.ma.cei.service.WebSocketService;
import cn.ma.cei.test.websocket.messages.EchoMessage;
import cn.ma.cei.test.websocket.processor.EchoProcessor;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

public class TestHandler implements IWebSocketHandler {
    private static final EchoProcessor EchoProcessor = new EchoProcessor();

    @Override
    public WebSocketService.MessageResult handle(String text, Buffer buffer, WebSocketClient client) {
        System.err.println("Receive request " + text);
        JsonObject jsonObject = new JsonObject(text);
        String op = jsonObject.getString("op");
        JsonObject param = jsonObject.getJsonObject("param");
        switch (op) {
            case "echo": {
                EchoMessage echoMessage = new EchoMessage();
                echoMessage.text = text;
                return WebSocketService.MessageResult.normal(echoMessage, EchoProcessor);
            }
        }
        return WebSocketService.MessageResult.error();
    }
}
