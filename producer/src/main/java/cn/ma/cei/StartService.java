/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei;

import cn.ma.cei.service.WebsocketService;
import cn.ma.cei.service.handler.GetExchangeSummary;
import cn.ma.cei.service.processors.ExInfoProcessor;
import cn.ma.cei.service.processors.InitProcessor;
import cn.ma.cei.service.processors.ModelTestProcessor;
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

        HttpServer httpServer = vertx.createHttpServer().requestHandler(router);
        WebsocketService.registerProcessor(new InitProcessor());
        WebsocketService.registerProcessor(new ExInfoProcessor());
        WebsocketService.registerProcessor(new ModelTestProcessor());

        WebsocketService websocketService = new WebsocketService();
        websocketService.startService(httpServer);
        httpServer.listen(8090);
        
//        HttpServer server = vertx.createHttpServer().requestHandler(router).websocketHandler(websocket -> {
//            String clientId = websocket.binaryHandlerID();
//            System.out.println("Connected: " + clientId);
//
//            websocket.frameHandler(handler -> {
//                if (!handler.isText()) {
//                    return;
//                }
//                String textData = handler.textData();
//                String currID = websocket.binaryHandlerID();
//                System.out.println("Rec: " + textData + "[" + currID + "]");
//                
//            });
//            
//            websocket.closeHandler(handler -> {
//                System.out.println("Disconnect: " + clientId);
//            });
//
//        }).listen(8090);
        System.out.println("Server started");
    }
}
