package cn.ma.cei.service.processors;

import cn.ma.cei.finalizer.XMLDatabase;
import cn.ma.cei.service.WebSocketClient;
import cn.ma.cei.service.WebSocketMessage;
import cn.ma.cei.service.WebSocketMessageProcessor;
import cn.ma.cei.service.messages.ExchangeInfoMessage;
import cn.ma.cei.service.response.ExchangeInfo;

public class ExchangeInfoProcessor extends WebSocketMessageProcessor {

    @Override
    public <T extends WebSocketMessage> void process(T message, WebSocketClient client) {
        ExchangeInfoMessage msg = (ExchangeInfoMessage)message;
        if (!XMLDatabase.containsExchange(msg.exchangeName)) {
            CommonProcessor.error(client, msg.requestID,"Cannot find exchange:" + msg.exchangeName);
            return;
        }
        ExchangeInfo exchangeInfo = new ExchangeInfo();
        exchangeInfo.name = msg.exchangeName;
        exchangeInfo.models.addAll(XMLDatabase.getModelSet(msg.exchangeName));
//        Set<String> clients = XMLDatabase.getClientSet(msg.param.exchangeName);
//        clients.forEach((clientName) -> {
//            ExchangeInfo.Client clientInfo = new ExchangeInfo.Client();
//            clientInfo.name = clientName;
////            xRestful client = XMLDatabase.getClient(msg.param.exchangeName, clientName);
////            clientInfo.interfaces.addAll(client.getInterfaceSet());
//            exchangeInfo.clients.add(clientInfo);
//        });
        CommonProcessor.response(client, msg.requestID,exchangeInfo);
    }
}
