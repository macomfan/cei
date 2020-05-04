package cn.ma.cei.test.restful.post;


import cn.ma.cei.service.IRestfulHandler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;

public class Echo implements IRestfulHandler {

    @Override
    public HttpMethod httpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String handle(HttpServerRequest request, Buffer body) {
        System.err.println("Process echo " + body.toJsonObject().toString());
        return body.toJsonObject().toString();
    }
}
