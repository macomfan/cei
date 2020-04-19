package cei.simulator.server.restful.post;

import cei.simulator.server.main;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class Inputs {
    public static void register() {
        main.routePost("/restful/post/inputs", (request, body) -> body.toJsonObject().toString());
    }
}
