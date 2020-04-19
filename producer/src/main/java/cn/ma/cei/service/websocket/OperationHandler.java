package cn.ma.cei.service.websocket;

import cn.ma.cei.service.IWebSocketHandler;
import cn.ma.cei.service.WebSocketService;
import cn.ma.cei.service.messages.ExchangeInfoMessage;
import cn.ma.cei.service.messages.ExchangeQueryMessage;
import cn.ma.cei.utils.Checker;
import com.alibaba.fastjson.JSONObject;
import io.vertx.core.buffer.Buffer;

public class OperationHandler implements IWebSocketHandler {
    @Override
    public WebSocketService.MessageResult handle(String text, Buffer buffer) {
        if (text == null) {
            return WebSocketService.MessageResult.error();
        }
        JSONObject json = JSONObject.parseObject(text);
        String type = json.getString("type");
        if (Checker.isEmpty(type)) {
            System.err.println("[Client] Error unknown request type");
            return WebSocketService.MessageResult.error();
        }
        JSONObject param = json.getJSONObject("param");
        if (param == null) {
            System.err.println("[Client] Error unknown request param");
            return WebSocketService.MessageResult.error();
        }

        int requestID = json.getIntValue("id");

        WebSocketService.MessageResult result = WebSocketService.MessageResult.error();
        switch (type) {
            case "ExInfo":
                ExchangeInfoMessage exchangeInfoMessage = param.toJavaObject(ExchangeInfoMessage.class);
                exchangeInfoMessage.requestID = requestID;
                result = WebSocketService.MessageResult.normal(exchangeInfoMessage);
                break;
            case "ExQuery":
                ExchangeQueryMessage exchangeQueryMessage = param.toJavaObject(ExchangeQueryMessage.class);
                exchangeQueryMessage.requestID = requestID;
                result = WebSocketService.MessageResult.normal(exchangeQueryMessage);
        }
        return result;
    }
}
