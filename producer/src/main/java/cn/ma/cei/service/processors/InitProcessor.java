/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.service.processors;

import cn.ma.cei.finalizer.XMLDatabase;
import cn.ma.cei.service.ProcessContext;
import cn.ma.cei.service.messages.InitMessage;
import cn.ma.cei.service.response.Exchanges;

/**
 *
 * @author u0151316
 */
public class InitProcessor extends ProcessorBase<InitMessage> {

    @Override
    public String getType() {
        return ProcessorBase.REQ;
    }

    @Override
    public String getCatalog() {
        return "init";
    }

    @Override
    public InitMessage newMessage() {
        return new InitMessage();
    }

    @Override
    public void process(ProcessContext context, InitMessage msg) {
//        List<String> exchanges = new LinkedList<>();
//        exchanges.add("binance");
//        exchanges.add("huobipro");
//        exchanges.add("cei");
//        JSONObject data = new JSONObject();
//        data.put("exchanges", exchanges);

//        JSONObject data = new JSONObject();
//
//        JSONObject exchang1 = new JSONObject();
//        exchang1.put("name", "binance");
//
//        JSONObject exchang2 = new JSONObject();
//        exchang2.put("name", "huobipro");
//
//        JSONObject exchang3 = new JSONObject();
//        exchang3.put("name", "cei");
//        List<String> models = new LinkedList<>();
//        models.add("Symbol");
//        models.add("LastTrade");
//
//        JSONObject huobiClient = new JSONObject();
//        huobiClient.put("name", "huobiClient");
//        List<String> restfuls = new LinkedList<>();
//        restfuls.add("getSymbols");
//        restfuls.add("getLastTrade");
//        huobiClient.put("restfuls", restfuls);
//        List<JSONObject> clients = new LinkedList<>();
//        clients.add(huobiClient);
//        JSONObject binanceClient = new JSONObject();
//        binanceClient.put("name", "binanceClient");
//        clients.add(binanceClient);
//
//        exchang3.put("models", models);
//        exchang3.put("client", clients);
//
//        List<JSONObject> exchangeList = new LinkedList<>();
//        exchangeList.add(exchang1);
//        exchangeList.add(exchang2);
//        exchangeList.add(exchang3);
//
//        data.put("exchanges", exchangeList);
        Exchanges ex = new Exchanges();
        ex.exchanges.addAll(XMLDatabase.getExchangeSet());
        context.response(ex);
    }
    
}
