package cn.ma.cei.service.restful;

import cn.ma.cei.service.IRestfulHandler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.handler.StaticHandler;

public class StaticPage implements IRestfulHandler {

    public String rootPath;

    public StaticPage(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public HttpMethod httpMethod() {
        return null;
    }

    @Override
    public String handle(HttpServerRequest request, Buffer body) {
        StaticHandler.create().setWebRoot(rootPath);
        return null;
    }
}
