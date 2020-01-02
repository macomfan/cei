/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cei.simulator.server;

import cei.simulator.server.models.ExchangeInfo;
import cei.simulator.server.models.LastTrade;
import cei.simulator.server.models.PlaceOrder;
import com.alibaba.fastjson.JSON;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

/**
 *
 * @author U0151316
 */
public class main {
    public static Vertx vertx = Vertx.vertx();
    
    public static void main(String[] args) {

        Router router = Router.router(vertx);
        ExchangeInfo.register(router);
        LastTrade.register(router);
        PlaceOrder.register(router);

        HttpServer server = vertx.createHttpServer().requestHandler(router).listen(8081);
        System.out.println("Server started");
    }
}
