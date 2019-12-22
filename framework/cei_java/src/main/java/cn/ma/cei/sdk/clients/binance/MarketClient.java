package cn.ma.cei.sdk.clients.binance;

import cn.ma.cei.sdk.impl.RestfulConnection;
import cn.ma.cei.sdk.impl.RestfulRequest;
import cn.ma.cei.sdk.impl.RestfulResponse;

public class MarketClient {

    private String currentDomain_ = "https://api.binance.com";

    public String getKLine(String symbol) {
//        RestfulRequestQueryStrings queryStrings = new RestfulRequestQueryStrings();
//        queryStrings.addQueryString("symbol", symbol);
//
//        RestfulRequest request = new RestfulRequest();
//        request.target = "/api/v3/klines";
//        request.method = RestfulRequest.Method.GET;
//        request.url = currentDomain_;
//        RestfulConnection connection = new RestfulConnection();
//        RestfulResponse response = connection.query(request);
        return "";
    }
}
