/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.service.restful;

import cn.ma.cei.service.IRestfulHandler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author U0151316
 */
public class GetExchangeSummary implements IRestfulHandler {
    @Override
    public HttpMethod httpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String handle(HttpServerRequest request, Buffer body) {
        JsonObject data = new JsonObject();
        data.put("status", "ok");
        JsonObject exchang1 = new JsonObject();
        exchang1.put("name", "binance");

        JsonObject exchang2 = new JsonObject();
        exchang2.put("name", "huobipro");

        JsonObject exchang3 = new JsonObject();
        exchang3.put("name", "cei");
        List<String> models = new LinkedList<>();
        models.add("Symbol");
        models.add("LastTrade");

        JsonObject huobiClient = new JsonObject();
        huobiClient.put("name", "huobiClient");
        List<String> restfuls = new LinkedList<>();
        restfuls.add("getSymbols");
        restfuls.add("getLastTrade");
        huobiClient.put("restfuls", restfuls);
        List<JsonObject> clients = new LinkedList<>();
        clients.add(huobiClient);
        JsonObject binanceClient = new JsonObject();
        binanceClient.put("name", "binanceClient");
        clients.add(binanceClient);

        exchang3.put("models", models);
        exchang3.put("client", clients);

        List<JsonObject> exchangeList = new LinkedList<>();
        exchangeList.add(exchang1);
        exchangeList.add(exchang2);
        exchangeList.add(exchang3);

        data.put("exchanges", exchangeList);
        return data.toString();
    }
}
