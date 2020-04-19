package cei.simulator.server.restful.get;

import cei.simulator.server.main;
import io.vertx.core.json.JsonObject;

public class InputsByGet {

    public static void register() {
        main.routeGet("/restful/get/inputsByGet", request -> {
            String name = request.getParam("Name");
            String number = request.getParam("Number");
            String price = request.getParam("Price");
            String status = request.getParam("Status");
            JsonObject object = new JsonObject();
            object.put("Name", name)
                    .put("Number", Integer.parseInt(number))
                    .put("Price", Double.valueOf(price))
                    .put("Status", Boolean.valueOf(status));
            return object.toString();
        });
    }

}
