package cei.simulator.server.websocket.request;

import cei.simulator.server.main;
import io.vertx.core.json.JsonObject;

public class Echo {
    public static void register() {
        main.routeWS("/websocket/request/echo", (clientID, binary, text) -> {
            JsonObject input = new JsonObject(text);

        });
    }
}
