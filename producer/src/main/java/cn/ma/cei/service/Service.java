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


    private void sendResponse(RoutingContext routingContext, String response) {
        if (Checker.isEmpty(response)) {
            return;
        }
        routingContext.response().putHeader("Access-Control-Allow-Origin", "*").end(response);
    }
    public Service registerRestful(String path, IRestfulHandler handler) {
        if (Checker.isEmpty(path)) {
            CEIErrors.showCodeFailure(handler.getClass(), "Cannot be register to the restful interface, path is null");
        }
        Handler<RoutingContext> httpHandler = routingContext -> {
            final String[] response = new String[1];
            if (routingContext.request().method() == HttpMethod.GET) {
                response[0] = handler.handle(routingContext.request(), null);
                sendResponse(routingContext, response[0]);
            } else if (routingContext.request().method() == HttpMethod.POST){
                routingContext.request().bodyHandler(body -> {
                    response[0] = handler.handle(routingContext.request(), body);
                    sendResponse(routingContext, response[0]);
                });
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

    public Service registerWebSocketNotification(String notificationName, WebSocketNotification notification) {
        websocketService.registerNotification(notificationName, notification);
        return this;
    }

    public void start(int port) {

        HttpServer httpServer = vertx.createHttpServer().requestHandler(router);
        websocketService.startService(httpServer);
        httpServer.listen(port);
        System.out.println(String.format("Server started on port %d", port));
    }
}
