/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei;

import cn.ma.cei.service.handler.GetExchangeSummary;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

/**
 *
 * @author u0151316
 */
public class StartService {

    public static Vertx vertx = Vertx.vertx();

    public static void main(String[] args) {
        Router router = Router.router(vertx);

        GetExchangeSummary.register(router);

        router.route("/api").handler((routingContext) -> {
            System.out.println("Get apis");
            routingContext.response().end("api");
        });
        router.route("/*").handler(StaticHandler.create().setWebRoot("C:/dev/cei/webroot"));
//        router.route(HttpMethod.GET, "/").handler(routingContext -> {
//            routingContext.response().end("Hello");
//        });
        HttpServer server = vertx.createHttpServer().requestHandler(router).websocketHandler(websocket -> {
            System.out.println("Connected");

        }).listen(8090);
        System.out.println("Server started");
    }
}
