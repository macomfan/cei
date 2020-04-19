package cei.simulator.server.restful;

import com.alibaba.fastjson.JSON;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

public class LastTrade {
    private final static String data = "{\n" +
            "\t\"Symbol\": \"btcusdt\",\n" +
            "\t\"Timestamp\": 1580652183,\n" +
            "\t\"Price\": 1.0,\n" +
            "\t\"Volume\": 1.0\n" +
            "}";

    public static void register(Router router) {
        router.route(HttpMethod.GET, "/api/v1/lastTrade").handler(routingContext -> {
            System.out.println("/api/v1/lastTrade");
            String symbol = routingContext.request().getParam("symbol");
            if (symbol == null) {
                Error error = new Error();
                error.ErrorCode = 100;
                error.Message = "Must define symbol";
                routingContext.response().end(JSON.toJSONString(error));
            }
            System.out.println("Symbol: " + symbol);
            routingContext.response().end(data);
        });
    }
}
