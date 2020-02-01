package cn.ma.cei.service.processors;

import cn.ma.cei.finalizer.XMLDatabase;
import cn.ma.cei.model.xSDK;
import cn.ma.cei.service.ProcessContext;
import cn.ma.cei.service.messages.ExchangeQueryMessage;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.xml.Convert;
import cn.ma.cei.xml.XmlToJson;

public class ExchangeQueryProcessor extends ProcessorBase<ExchangeQueryMessage> {
    @Override
    public String getType() {
        return ProcessorBase.REQ;
    }

    @Override
    public String getCatalog() {
        return "ExQuery";
    }

    @Override
    public ExchangeQueryMessage newMessage() {
        return new ExchangeQueryMessage();
    }

    @Override
    public void process(ProcessContext context, ExchangeQueryMessage msg) {
        String exchangeName = msg.param.exchangeName;
        if (Checker.isEmpty(exchangeName)) {
            context.error("Invalid exchange name");
            return;
        }
        xSDK sdk = XMLDatabase.getSDK(exchangeName);
        if (sdk == null) {
            context.error("Cannot find exchange: " + exchangeName);
            return;
        }
        XmlToJson xmlToJson = new XmlToJson();
        Convert.doConvert(xmlToJson, sdk);
        context.response(xmlToJson.getJsonObject());
    }
}
