package cei.simulator.server.restful;

import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

public class Timestamp {
    private final static String data = "{\n" +
            "\t\"Timestamp\": 1580652183\n" +
            "}";

    public static void register(Router router) {
        router.route(HttpMethod.GET, "/api/v1/timestamp").handler(routingContext -> {
            System.out.println("/api/v1/timestamp");
            routingContext.response().end(data);
        });

    }
}
