package cei.simulator.server.models;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

public class Timestamp {
    public static void register(Router router) {


        router.route(HttpMethod.GET, "/api/v1/timestamp").handler(routingContext -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Timestamp", System.currentTimeMillis());
            routingContext.response().end(jsonObject.toJSONString());
        });

    }
}
