package cn.ma.cei.test.restful.get;

import cn.ma.cei.service.IRestfulHandler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonArray;

public class TestArray implements IRestfulHandler {

    @Override
    public HttpMethod httpMethod() {
        return HttpMethod.GET;
    }
    @Override
    public String handle(HttpServerRequest request, Buffer body) {
        JsonArray array = new JsonArray();
        array.add("test").add(123).add(123.456).add(true);
        return array.toString();
    }
}
