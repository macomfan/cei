package cn.ma.cei.test.restful.get;


import cn.ma.cei.service.IRestfulHandler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;

public class InputsByGet implements IRestfulHandler {

    @Override
    public HttpMethod httpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String handle(HttpServerRequest request, Buffer body) {
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
    }
}
