/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

/**
 *
 * @author u0151316
 */
public class startservice {

    public static Vertx vertx = Vertx.vertx();

    public static void main(String[] args) {
        Router router = Router.router(vertx);
        router.route("/").handler(StaticHandler.create().setWebRoot("C:/dev/cei/webroot"));
//        router.route(HttpMethod.GET, "/").handler(routingContext -> {
//            routingContext.response().end("Hello");
//        });
        HttpServer server = vertx.createHttpServer().requestHandler(router).listen(8080);
        System.out.println("Server started");
    }
}
