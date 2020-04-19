package cn.ma.cei.test.restful.post;


import cn.ma.cei.service.IRestfulHandler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;

import java.util.Map;

public class Authentication implements IRestfulHandler {

    @Override
    public HttpMethod httpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String handle(HttpServerRequest request, Buffer body) {
        for (Map.Entry<String, String> entry : request.params().entries()) {

        }
        System.out.println(body.toJsonObject().toString());
        return "";
    }
}
