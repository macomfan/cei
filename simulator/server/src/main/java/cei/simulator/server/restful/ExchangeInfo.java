package cei.simulator.server.restful;

import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

public class ExchangeInfo {
    private final static String data = "{\n" +
            "\t\"ExName\": \"TestExchange\",\n" +
            "\t\"Timestamp\": 1580652183,\n" +
            "\t\"Symbols\": [{\n" +
            "\t\t\t\"Name\": \"btcusdt\",\n" +
            "\t\t\t\"OCOAllowed\": true,\n" +
            "\t\t\t\"Precision\": 1,\n" +
            "\t\t\t\"Status\": \"O\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"Name\": \"ethusdt\",\n" +
            "\t\t\t\"OCOAllowed\": false,\n" +
            "\t\t\t\"Precision\": 2,\n" +
            "\t\t\t\"Status\": \"P\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"Name\": \"eosusdt\",\n" +
            "\t\t\t\"OCOAllowed\": true,\n" +
            "\t\t\t\"Precision\": 3,\n" +
            "\t\t\t\"Status\": \"Q\"\n" +
            "\t\t}\n" +
            "\t]\n" +
            "}";


    public static void register(Router router) {
        router.route(HttpMethod.GET, "/api/v1/exchangeInfo").handler(routingContext -> {
            System.out.println("/api/v1/exchangeInfo");
            routingContext.response().end(data);
        });

    }
}
