package cn.ma.cei.test.restful.get;

import cn.ma.cei.service.IRestfulHandler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class InfoList implements IRestfulHandler {
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

    @Override
    public HttpMethod httpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String handle(HttpServerRequest request, Buffer body) {
        JsonArray infoList = new JsonArray();
        JsonObject info1 = new JsonObject().put("Name", "Info1").put("InfoValue", "Value1");
        JsonObject info2 = new JsonObject().put("Name", "Info2").put("InfoValue", "Value2");
        JsonObject info3 = new JsonObject().put("Name", "Info3").put("InfoValue", "Value3");
        infoList.add(info1).add(info2).add(info3);
        JsonObject object = new JsonObject();
        object.put("Name", "InfoList")
                .put("Values", infoList);
        return object.toString();
    }
}
