package cn.ma.cei.service.websocket;

import cn.ma.cei.service.IWebSocketHandler;
import cn.ma.cei.service.WebSocketClient;
import cn.ma.cei.service.WebSocketService;
import cn.ma.cei.service.messages.ExchangeInfoMessage;
import cn.ma.cei.service.messages.ExchangeQueryMessage;
import cn.ma.cei.service.processors.ExchangeInfoProcessor;
import cn.ma.cei.service.processors.ExchangeQueryProcessor;
import cn.ma.cei.utils.Checker;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

public class OperationHandler implements IWebSocketHandler {
    private static final ExchangeInfoProcessor EXCHANGE_INFO_PROCESSOR = new ExchangeInfoProcessor();
    private static final ExchangeQueryProcessor EXCHANGE_QUERY_PROCESSOR = new ExchangeQueryProcessor();

    @Override
    public WebSocketService.MessageResult handle(String text, Buffer buffer, WebSocketClient client) {
        if (text == null) {
            return WebSocketService.MessageResult.error();
        }
        JsonObject json = new JsonObject(text);
        String type = json.getString("type");
        if (Checker.isEmpty(type)) {
            System.err.println("[Client] Error unknown request type");
            return WebSocketService.MessageResult.error();
        }
        JsonObject param = json.getJsonObject("param");
        if (param == null) {
            System.err.println("[Client] Error unknown request param");
            return WebSocketService.MessageResult.error();
        }

        int requestID = json.getInteger("id");

        WebSocketService.MessageResult result = WebSocketService.MessageResult.error();
        switch (type) {
            case "ExInfo":
                ExchangeInfoMessage exchangeInfoMessage = param.mapTo(ExchangeInfoMessage.class);
                exchangeInfoMessage.requestID = requestID;
                result = WebSocketService.MessageResult.normal(exchangeInfoMessage, EXCHANGE_INFO_PROCESSOR);
                break;
            case "ExQuery":
                ExchangeQueryMessage exchangeQueryMessage = param.mapTo(ExchangeQueryMessage.class);
                exchangeQueryMessage.requestID = requestID;
                result = WebSocketService.MessageResult.normal(exchangeQueryMessage, EXCHANGE_QUERY_PROCESSOR);
        }
        return result;
    }
}
