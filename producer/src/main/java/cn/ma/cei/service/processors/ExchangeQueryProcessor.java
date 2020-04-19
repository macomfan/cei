package cn.ma.cei.service.processors;

import cn.ma.cei.finalizer.XMLDatabase;
import cn.ma.cei.model.xSDK;
import cn.ma.cei.service.WebSocketClient;
import cn.ma.cei.service.WebSocketMessage;
import cn.ma.cei.service.WebSocketMessageProcessor;
import cn.ma.cei.service.messages.ExchangeQueryMessage;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.xml.Convert;
import cn.ma.cei.xml.XmlToJson;

public class ExchangeQueryProcessor extends WebSocketMessageProcessor {

    @Override
    public <T extends WebSocketMessage> void process(T message, WebSocketClient client) {
        ExchangeQueryMessage msg = (ExchangeQueryMessage) message;
        String exchangeName = msg.exchangeName;
        if (Checker.isEmpty(exchangeName)) {
            CommonProcessor.error(client, msg.requestID, "Invalid exchange name");
            return;
        }
        xSDK sdk = XMLDatabase.getSDK(exchangeName);
        if (sdk == null) {
            CommonProcessor.error(client, msg.requestID, "Cannot find exchange:" + exchangeName);
            return;
        }
        XmlToJson xmlToJson = new XmlToJson();
        Convert.doConvert(xmlToJson, sdk);
        CommonProcessor.response(client, msg.requestID, xmlToJson.getJsonObject());
    }
}
