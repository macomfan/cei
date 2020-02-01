/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.service.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author U0151316
 */
public class GetExchangeSummary {

    public static void register(Router router) {

        JSONObject data = new JSONObject();
        data.put("status", "ok");
        JSONObject exchang1 = new JSONObject();
        exchang1.put("name", "binance");

        JSONObject exchang2 = new JSONObject();
        exchang2.put("name", "huobipro");

        JSONObject exchang3 = new JSONObject();
        exchang3.put("name", "cei");
        List<String> models = new LinkedList<>();
        models.add("Symbol");
        models.add("LastTrade");

        JSONObject huobiClient = new JSONObject();
        huobiClient.put("name", "huobiClient");
        List<String> restfuls = new LinkedList<>();
        restfuls.add("getSymbols");
        restfuls.add("getLastTrade");
        huobiClient.put("restfuls", restfuls);
        List<JSONObject> clients = new LinkedList<>();
        clients.add(huobiClient);
        JSONObject binanceClient = new JSONObject();
        binanceClient.put("name", "binanceClient");
        clients.add(binanceClient);
        
        exchang3.put("models", models);
        exchang3.put("client", clients);

        List<JSONObject> exchangeList = new LinkedList<>();
        exchangeList.add(exchang1);
        exchangeList.add(exchang2);
        exchangeList.add(exchang3);

        data.put("exchanges", exchangeList);

        router.route(HttpMethod.GET, "/api/getExchangeSummary").handler(routingContext -> {
            System.out.println("Send ExchangeSummary");
            String str = JSON.toJSONString(data);
            routingContext.response().putHeader("Access-Control-Allow-Origin", "*").end(str);
        });
    }
}
