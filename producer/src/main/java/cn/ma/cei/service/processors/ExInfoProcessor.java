package cn.ma.cei.service.processors;

import cn.ma.cei.service.ProcessContext;
import cn.ma.cei.service.messages.ExInfoMessage;
import cn.ma.cei.service.response.ExchangeInfo;

public class ExInfoProcessor extends ProcessorBase<ExInfoMessage> {

    @Override
    public String getType() {
        return ProcessorBase.REQ;
    }

    @Override
    public String getCatalog() {
        return "ExInfo";
    }

    @Override
    public ExInfoMessage newMessage() {
        return new ExInfoMessage();
    }

    @Override
    public void process(ProcessContext context, ExInfoMessage msg) {
        ExchangeInfo exchangeInfo = new ExchangeInfo();
        exchangeInfo.name = msg.param.name;
        exchangeInfo.models.add("Symbol");
        exchangeInfo.models.add("GetSymbol");
        ExchangeInfo.Client marketClient = new ExchangeInfo.Client();
        marketClient.name = "MarketClient";
        marketClient.interfaces.add("getSymbol");
        exchangeInfo.clients.add(marketClient);
        context.response(exchangeInfo);
    }
}
