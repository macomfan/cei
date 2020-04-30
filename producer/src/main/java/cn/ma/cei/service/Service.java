package cn.ma.cei.service;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.utils.Checker;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;


public class Service {

    private static final Vertx vertx = Vertx.vertx();
    private final Router router = Router.router(vertx);
    private final WebSocketService websocketService = new WebSocketService();

    public Service registerRestful(String path, IRestfulHandler handler) {
        if (Checker.isEmpty(path)) {
            CEIErrors.showCodeFailure(handler.getClass(), "Cannot be register to the restful interface, path is null");
        }
        Handler<RoutingContext> httpHandler = routingContext -> {
            final String[] response = new String[1];
            if (routingContext.request().method() == HttpMethod.GET) {
                response[0] = handler.handle(routingContext.request(), null);
            } else {
                routingContext.request().bodyHandler(body -> {
                    response[0] = handler.handle(routingContext.request(), body);
                });
            }
            if (response[0] != null) {
                routingContext.response().putHeader("Access-Control-Allow-Origin", "*").end(response[0]);
            }
        };
        if (handler.httpMethod() != null) {
            router.route(handler.httpMethod(), path).handler(httpHandler);
        } else {
            router.route(path).handler(httpHandler);
        }
        return this;
    }

    public Service registerWebSocket(String channel, IWebSocketHandler channelHandler) {
        websocketService.registerHandler(channel, channelHandler);
        return this;
    }

    public void start(int port) {

        HttpServer httpServer = vertx.createHttpServer().requestHandler(router);
//        WebSocketService.registerProcessor(new InitProcessor());
//        WebSocketService.registerProcessor(new ExchangeInfoProcessor());
//        WebSocketService.registerProcessor(new ModelTestProcessor());
//        WebSocketService.registerProcessor(new ExchangeQueryProcessor());
//        WebSocketService.registerProcessor(new ModelUpdateProcessor());

        websocketService.startService(httpServer);
        httpServer.listen(port);
        System.out.println(String.format("Server started on port %d", port));
    }
}
