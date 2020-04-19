package cn.ma.cei.test.restful.get;

import cn.ma.cei.service.IRestfulHandler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;

public class SimpleInfo implements IRestfulHandler {

    @Override
    public HttpMethod httpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String handle(HttpServerRequest request, Buffer body) {
        JsonObject object = new JsonObject();
        object.put("Name", "test")
                .put("Number", 123)
                .put("Price", 123.456)
                .put("Status", true);
        return object.toString();
    }
}
