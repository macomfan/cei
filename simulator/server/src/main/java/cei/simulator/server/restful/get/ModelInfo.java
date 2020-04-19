package cei.simulator.server.restful.get;

import cei.simulator.server.main;
import io.vertx.core.json.JsonObject;

public class ModelInfo {

    /*
{
	"Name": "ModelInfo",
	"DataL1": {
		"DataL2": {
			"Value": {
				"Name": "ModelValue",
				"Value": 123
			}
		}
	}
}
     */

    public static void register() {
        main.routeGet("/restful/get/modelInfo", request -> {
            JsonObject modelValue = new JsonObject();
            modelValue.put("Name", "ModelValue")
                    .put("Value", 123);
            JsonObject dataValueL2 = new JsonObject();
            dataValueL2.put("Value", modelValue);
            JsonObject dataValueL1 = new JsonObject();
            dataValueL1.put("DataL2", dataValueL2);
            JsonObject object = new JsonObject();
            object.put("Name", "ModelInfo")
                    .put("DataL1", dataValueL1);
            return object.toString();
        });
    }
}
