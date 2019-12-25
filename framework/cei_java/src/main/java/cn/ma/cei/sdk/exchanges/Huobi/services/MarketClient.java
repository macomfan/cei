package cn.ma.cei.sdk.exchanges.huobi.services;

import cn.ma.cei.sdk.exchanges.Huobi.models.LastTradeTick;
import cn.ma.cei.sdk.exchanges.Huobi.models.SymbolsData;
import cn.ma.cei.sdk.impl.JsonWrapper;
import cn.ma.cei.sdk.exchanges.Huobi.models.LastTradeData;
import cn.ma.cei.sdk.exchanges.Huobi.models.Symbols;
import cn.ma.cei.sdk.impl.RestfulResponse;
import cn.ma.cei.sdk.impl.RestfulConnection;
import cn.ma.cei.sdk.exchanges.Huobi.models.LastTrade;
import cn.ma.cei.sdk.impl.RestfulRequest;
import cn.ma.cei.sdk.exchanges.Huobi.models.Currencies;

public class MarketClient {


    public Symbols getSymbol() {
        RestfulRequest request = new RestfulRequest(this.options);
        request.setTarget("/v1/common/symbols");
        request.method = RestfulRequest.Method.GET;
        RestfulResponse response = RestfulConnection.connect(request);
        Symbols symbols_var = new Symbols();
        JsonWrapper root_obj = new JsonWrapper(response);
        symbols_var.status = root_obj.getString("status");
        JsonWrapper data_obj = root_obj.getArray("data");
        for(JsonWrapper item in data_obj.getItems()) {
            SymbolsData symbolsData_var = new SymbolsData();
            symbolsData_var.base_currency = item.getString("base-currency");
            if (symbols_var.data == null) {
                symbols_var.data = new List<SymbolsData>;
            }
            symbols_var.data.add(symbolsData_var);
        };
        return symbols_var;
    }
    

    public Currencies getCurrencies() {
        RestfulRequest request = new RestfulRequest(this.options);
        request.setTarget("/v1/common/currencys");
        request.method = RestfulRequest.Method.GET;
        RestfulResponse response = RestfulConnection.connect(request);
        Currencies currencies_var = new Currencies();
        JsonWrapper root_obj = new JsonWrapper(response);
        currencies_var.status = root_obj.getString("status");
        currencies_var.data = root_obj.getStringArray("data");
        return currencies_var;
    }
    

    public LastTrade getLastTrade(String symbol) {
        RestfulRequest request = new RestfulRequest(this.options);
        request.setTarget("/market/trade");
        request.method = RestfulRequest.Method.GET;
        request.addQueryString("symbol", symbol);
        RestfulResponse response = RestfulConnection.connect(request);
        LastTrade lastTrade_var = new LastTrade();
        JsonWrapper root_obj = new JsonWrapper(response);
        lastTrade_var.status = root_obj.getString("status");
        JsonWrapper tick_obj = root_obj.getObject("tick");
        LastTradeTick lastTradeTick_var = new LastTradeTick();
        lastTradeTick_var.ts = tick_obj.getString("ts");
        JsonWrapper data_obj = tick_obj.getArray("data");
        for(JsonWrapper item in data_obj.getItems()) {
            LastTradeData lastTradeData_var = new LastTradeData();
            lastTradeData_var.direction = item.getString("direction");
            if (lastTradeTick_var.data == null) {
                lastTradeTick_var.data = new List<LastTradeData>;
            }
            lastTradeTick_var.data.add(lastTradeData_var);
        };
        return lastTrade_var;
    }
    

}
