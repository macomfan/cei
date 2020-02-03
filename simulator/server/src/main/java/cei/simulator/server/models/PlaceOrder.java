/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cei.simulator.server.models;

import com.alibaba.fastjson.JSON;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

/**
 * @author u0151316
 */
public class PlaceOrder {

    private final static String data = "[12345]";

    public static void register(Router router) {

        router.route(HttpMethod.POST, "/api/v1/placeOrder").handler(routingContext -> {
            routingContext.request().bodyHandler(body -> {
                System.out.println("/api/v1/placeOrder");
                System.out.println("Post: " + body.toJsonObject().toString());
                routingContext.response().end(data);
            });
        });
    }
}
