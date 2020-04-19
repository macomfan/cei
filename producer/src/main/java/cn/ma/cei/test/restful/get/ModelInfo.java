package cn.ma.cei.test.restful.get;

import cn.ma.cei.service.IRestfulHandler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;

public class ModelInfo implements IRestfulHandler {

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

    @Override
    public HttpMethod httpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String handle(HttpServerRequest request, Buffer body) {
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
    }
}
