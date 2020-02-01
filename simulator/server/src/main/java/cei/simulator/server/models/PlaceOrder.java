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
 *
 * @author u0151316
 */
public class PlaceOrder {

    public int OrderId = 12345;

    public static void register(Router router) {

        router.route(HttpMethod.POST, "/api/v1/placeOrder").handler(routingContext -> {
            routingContext.request().bodyHandler(body -> {
                System.out.println("Post: " + body.toJsonObject().toString());
                System.out.println("Response: " + JSON.toJSONString(new PlaceOrder()));
                routingContext.response().end(JSON.toJSONString(new PlaceOrder()));
            });
        });
    }
}
