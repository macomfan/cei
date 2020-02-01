package cn.ma.cei.service.processors;

import cn.ma.cei.finalizer.XMLDatabase;
import cn.ma.cei.model.xRestful;
import cn.ma.cei.service.ProcessContext;
import cn.ma.cei.service.messages.ExchangeInfoMessage;
import cn.ma.cei.service.response.ExchangeInfo;
import cn.ma.cei.service.response.Exchanges;

import java.util.Set;

public class ExchangeInfoProcessor extends ProcessorBase<ExchangeInfoMessage> {

    @Override
    public String getType() {
        return ProcessorBase.REQ;
    }

    @Override
    public String getCatalog() {
        return "ExInfo";
    }

    @Override
    public ExchangeInfoMessage newMessage() {
        return new ExchangeInfoMessage();
    }

    @Override
    public void process(ProcessContext context, ExchangeInfoMessage msg) {
        if (!XMLDatabase.isExchangeExist(msg.param.exchangeName)) {
            context.error("Cannot find exchange:" + msg.param.exchangeName);
            return;
        }
        ExchangeInfo exchangeInfo = new ExchangeInfo();
        exchangeInfo.name = msg.param.exchangeName;
        exchangeInfo.models.addAll(XMLDatabase.getModelSet(msg.param.exchangeName));
        Set<String> clients = XMLDatabase.getClientSet(msg.param.exchangeName);
        clients.forEach((clientName) -> {
            ExchangeInfo.Client clientInfo = new ExchangeInfo.Client();
            clientInfo.name = clientName;
            xRestful client = XMLDatabase.getClient(msg.param.exchangeName, clientName);
            clientInfo.interfaces.addAll(client.getInterfaceSet());
            exchangeInfo.clients.add(clientInfo);
        });
        context.response(exchangeInfo);
    }
}
