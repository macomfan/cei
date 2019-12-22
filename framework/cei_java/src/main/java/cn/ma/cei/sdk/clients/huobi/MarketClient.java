package cn.ma.cei.sdk.clients.huobi;

import cn.ma.cei.sdk.impl.RawData;
import cn.ma.cei.sdk.impl.RestfulConnection;
import cn.ma.cei.sdk.impl.RestfulRequest;
import cn.ma.cei.sdk.impl.RestfulResponse;

public class MarketClient {

    private String currentDomain_ = "https://api.huobi.so";

    public String getHistoryKLine() {
        RestfulRequest request = new RestfulRequest();
        request.target = "/v1/common/currencys";
        request.method = RestfulRequest.Method.GET;
        request.url = currentDomain_;
        request.addHeader("accept-encoding", "gzip");
        request.addQueryString(currentDomain_, currentDomain_);
        RestfulConnection connection = new RestfulConnection();
        RestfulResponse response = connection.query(request);
        return response.getString();
    }

    public RawData getCurrencyInfo(String currency, Boolean authorizedUser) {
        RestfulRequest request = new RestfulRequest();
        request.target = "/v1/common/currencys";
        request.method = RestfulRequest.Method.GET;
        request.url = currentDomain_;
        request.addHeader("accept-encoding", "gzip");
        request.addQueryString("currency", currency);
        request.addQueryString("authorizedUser", authorizedUser);
        RestfulConnection connection = new RestfulConnection();
        RestfulResponse response = connection.query(request);
        return response.getRawData();
    }
}
