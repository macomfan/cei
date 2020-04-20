package cn.ma.cei.service.processors;

import cn.ma.cei.service.WebSocketClient;
import cn.ma.cei.service.response.IResponse;
import io.vertx.core.json.JsonObject;

public class CommonProcessor {
    public static void response(WebSocketClient client, int requestID, JsonObject json) {
        JsonObject response = new JsonObject();
        response.put("status", "ok");
        response.put("id", requestID);
        response.put("data", json);
        client.send(response);
    }

    public static void response(WebSocketClient client, int requestID, IResponse data) {
        JsonObject response = new JsonObject();
        response.put("status", "ok");
        response.put("id", requestID);
        response.put("data", JsonObject.mapFrom(data));
        client.send(response);
    }

    public static void error(WebSocketClient client, int requestID, String code) {
        JsonObject response = new JsonObject();
        response.put("status", "err");
        response.put("id", requestID);
        response.put("data", code);
        client.send(response);
    }
}
