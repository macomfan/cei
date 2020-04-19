package cei.simulator.server.restful.get;

import cei.simulator.server.main;
import io.vertx.core.json.JsonObject;

public class SimpleInfo {

    public static void register() {

        main.routeGet("/restful/get/simpleInfo", request -> {
            JsonObject object = new JsonObject();
            object.put("Name", "test")
                    .put("Number", 123)
                    .put("Price", 123.456)
                    .put("Status", true);
            return object.toString();
        });
    }
}
