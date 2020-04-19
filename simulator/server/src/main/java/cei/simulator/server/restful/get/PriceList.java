package cei.simulator.server.restful.get;

import cei.simulator.server.main;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class PriceList {

    /*
{
	"Name": "PriceList",
	"Prices": [
		[123.456, 0.1],
		[456.789, 0.2]
	]
}
     */


    public static void register() {

        main.routeGet("/restful/get/priceList", request -> {
            JsonArray priceList = new JsonArray();
            JsonArray price1 = new JsonArray().add(123.456).add(0.1);
            JsonArray price2 = new JsonArray().add(456.789).add(0.2);
            priceList.add(price1).add(price2);
            JsonObject object = new JsonObject();
            object.put("Name", "PriceList")
                    .put("Prices", priceList);
            return object.toString();
        });
    }
}