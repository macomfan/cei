package cei.simulator.server.models;

import com.alibaba.fastjson.JSON;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

import java.util.LinkedList;
import java.util.List;

public class ExchangeInfo {
    public String ExchangeName = "TestExchange";
    public long Timestamp = 123;
    public List<Symbol> Symbols = new LinkedList<>();

    public static void register(Router router) {
        ExchangeInfo data = new ExchangeInfo();
        Symbol btc = new Symbol();
        btc.Name = "btcusdt";
        btc.OCOAllowed = true;
        btc.Precision = 1;
        btc.Status = "O";
        Symbol eth = new Symbol();
        eth.Name = "ethusdt";
        eth.OCOAllowed = false;
        eth.Precision = 2;
        eth.Status = "C";
        Symbol eos = new Symbol();
        eos.Name = "eosusdt";
        eos.OCOAllowed = false;
        eos.Precision = 3;
        eos.Status = "A";
        data.Symbols.add(btc);
        data.Symbols.add(eth);
        data.Symbols.add(eos);

        router.route(HttpMethod.GET, "/api/v1/exchangeInfo").handler(routingContext -> {
            String str = JSON.toJSONString(data);
            routingContext.response().end(str);
        });

    }
}
