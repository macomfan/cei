/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.service;

import cn.ma.cei.service.WSConnection;
import cn.ma.cei.service.WebsocketService;
import cn.ma.cei.service.messages.MessageBase;
import cn.ma.cei.service.response.IResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 *
 * @author u0151316
 */
public class ProcessContext {
    public String clientID;
    public WSConnection connection;
    public long requestID;

    public void response(JSONObject json) {
        JSONObject response = new JSONObject();
        response.put("status", "ok");
        response.put("id", requestID);
        response.put("data", json);
        connection.send(response);
    }

    public void response(IResponse data) {
        JSONObject response = new JSONObject();
        response.put("status", "ok");
        response.put("id", requestID);
        response.put("data", JSON.toJSON(data));
        connection.send(response);
    }

    public void error(int code) {
        JSONObject response = new JSONObject();
        response.put("status", "ok");
        response.put("id", requestID);
        response.put("data", code);
        connection.send(response);
    }
}
