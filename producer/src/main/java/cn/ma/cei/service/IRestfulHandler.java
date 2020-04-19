package cn.ma.cei.service;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;

public interface IRestfulHandler {
    HttpMethod httpMethod();

    String handle(HttpServerRequest request, Buffer body);
}
