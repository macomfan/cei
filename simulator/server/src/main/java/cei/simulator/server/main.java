/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cei.simulator.server;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

/**
 *
 * @author U0151316
 */
public class main {
    public static Vertx vertx = Vertx.vertx();
    
    public static void main(String[] args) {
        HttpServer server = vertx.createHttpServer().listen(8081);
        //server.listen(8081);
        //server.requestHandler(request -> {});
        //Route route = Route.
        
    }
}
