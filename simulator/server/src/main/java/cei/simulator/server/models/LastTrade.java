package cei.simulator.server.models;

import com.alibaba.fastjson.JSON;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class LastTrade {
    public BigDecimal Price;
    public BigDecimal Volume;
    public Long Timestamp;
    public String Symbol;

    private static Map<String, LastTrade> map = new HashMap<>();
    public static LastTrade getLastTrade(String symbol) {
        if (map.containsKey(symbol)) {
            return map.get(symbol);
        }
        return null;
    }
    public static void addLastTrade(LastTrade lastTrade) {
        map.put(lastTrade.Symbol, lastTrade);
    }

    public static void register(Router router) {
        LastTrade btc = new LastTrade();
        btc.Price = new BigDecimal("100.0001");
        btc.Volume = new BigDecimal("0.005");
        btc.Timestamp = 123456L;
        btc.Symbol = "btcusdt";
        LastTrade eth = new LastTrade();
        eth.Price = new BigDecimal("2.0002");
        eth.Volume = new BigDecimal("0.025");
        eth.Timestamp = 123411L;
        eth.Symbol = "ethusdt";
        LastTrade eos = new LastTrade();
        eos.Price = new BigDecimal("3.0003");
        eos.Volume = new BigDecimal("0.325");
        eos.Timestamp = 123411L;
        eos.Symbol = "ethusdt";
        LastTrade.addLastTrade(btc);
        LastTrade.addLastTrade(eth);
        LastTrade.addLastTrade(eos);

        router.route(HttpMethod.GET, "/api/v1/lastTrade").handler(routingContext -> {
            String symbol = routingContext.request().getParam("symbol");
            if (symbol == null) {
                Error error = new Error();
                error.ErrorCode = 100;
                error.Message = "Must define symbol";
                routingContext.response().end(JSON.toJSONString(error));
            }
            LastTrade lastTrade = LastTrade.getLastTrade(symbol);
            if (lastTrade != null) {
                routingContext.response().end(JSON.toJSONString(lastTrade));
            } else {
                Error error = new Error();
                error.ErrorCode = 100;
                error.Message = "Cannot find symbol";
                routingContext.response().end(JSON.toJSONString(error));
            }
        });
    }
}
