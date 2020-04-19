package cn.ma.cei.service.processors;

import cn.ma.cei.service.WebSocketClient;
import cn.ma.cei.service.response.IResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class CommonProcessor {
    public static void response(WebSocketClient client, int requestID, JSONObject json) {
        JSONObject response = new JSONObject();
        response.put("status", "ok");
        response.put("id", requestID);
        response.put("data", json);
        client.send(response);
    }

    public static void response(WebSocketClient client, int requestID, IResponse data) {
        JSONObject response = new JSONObject();
        response.put("status", "ok");
        response.put("id", requestID);
        response.put("data", JSON.toJSON(data));
        client.send(response);
    }

    public static void error(WebSocketClient client, int requestID, String code) {
        JSONObject response = new JSONObject();
        response.put("status", "err");
        response.put("id", requestID);
        response.put("data", code);
        client.send(response);
    }
}
