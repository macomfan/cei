package cei.simulator.server.restful.get;

import cei.simulator.server.main;
import io.vertx.core.json.JsonArray;

public class TestArray {
    public static void register() {
        main.routeGet("/restful/get/testArray", request -> {
            JsonArray array = new JsonArray();
            array.add("test").add(123).add(123.456).add(true);
            return array.toString();
        });
    }
}
