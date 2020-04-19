package cei.simulator.server.restful.get;

import cei.simulator.server.main;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class InfoList {
    /*
{
	"Name": "InfoList",
	"Values": [{
		"Name": "Info1",
		"InfoValue": "Value1"
	}, {
		"Name": "Info2",
		"InfoValue": "Value2"
	}, {
		"Name": "Info3",
		"InfoValue": "Value3"
	}]
}
     */

    public static void register() {
        main.routeGet("/restful/get/infoList", request -> {
            JsonArray infoList = new JsonArray();
            JsonObject info1 = new JsonObject().put("Name", "Info1").put("InfoValue", "Value1");
            JsonObject info2 = new JsonObject().put("Name", "Info2").put("InfoValue", "Value2");
            JsonObject info3 = new JsonObject().put("Name", "Info3").put("InfoValue", "Value3");
            infoList.add(info1).add(info2).add(info3);
            JsonObject object = new JsonObject();
            object.put("Name", "InfoList")
                    .put("Values", infoList);
            return object.toString();
        });
    }
}
