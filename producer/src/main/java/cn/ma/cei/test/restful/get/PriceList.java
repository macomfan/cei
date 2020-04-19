package cn.ma.cei.test.restful.get;

import cn.ma.cei.service.IRestfulHandler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class PriceList implements IRestfulHandler {

    /*
{
	"Name": "PriceList",
	"Prices": [
		[123.456, 0.1],
		[456.789, 0.2]
	]
}
     */


    @Override
    public HttpMethod httpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String handle(HttpServerRequest request, Buffer body) {
        JsonArray priceList = new JsonArray();
        JsonArray price1 = new JsonArray().add(123.456).add(0.1);
        JsonArray price2 = new JsonArray().add(456.789).add(0.2);
        priceList.add(price1).add(price2);
        JsonObject object = new JsonObject();
        object.put("Name", "PriceList")
                .put("Prices", priceList);
        return object.toString();
    }
}