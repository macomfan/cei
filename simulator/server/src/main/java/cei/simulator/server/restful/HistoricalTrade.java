package cei.simulator.server.restful;

import com.alibaba.fastjson.JSON;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

public class HistoricalTrade {
    private final static String data = "[{\n" +
            "\t\t\"price\": 1.0,\n" +
            "\t\t\"volume\": 1.0\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"price\": 2.0,\n" +
            "\t\t\"volume\": 2.0\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"price\": 3.0,\n" +
            "\t\t\"volume\": 3.0\n" +
            "\t}\n" +
            "]";

    public static void register(Router router) {
        router.route(HttpMethod.GET, "/api/v1/historicalTrade").handler(routingContext -> {
            System.out.println("/api/v1/historicalTrade");
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
