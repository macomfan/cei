package cn.ma.cei.test.websocket.request;

import cn.ma.cei.service.IWebSocketHandler;
import cn.ma.cei.service.WebSocketService;
import cn.ma.cei.test.websocket.messages.EchoMessage;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

public class RequestHandler implements IWebSocketHandler {
    @Override
    public WebSocketService.MessageResult handle(String text, Buffer buffer) {
        System.err.println("Receive request " + text);
        JsonObject jsonObject = new JsonObject(text);
        String op = jsonObject.getString("op");
        JsonObject param = jsonObject.getJsonObject("param");
        switch (op) {
            case "echo": {
                EchoMessage echoMessage = new EchoMessage();
                echoMessage.text = text;
                return WebSocketService.MessageResult.normal(echoMessage);
            }
        }
        return WebSocketService.MessageResult.error();
    }
}
