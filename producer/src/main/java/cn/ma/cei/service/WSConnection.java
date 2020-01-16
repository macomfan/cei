/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.service;

import cn.ma.cei.service.messages.InitMessage;
import cn.ma.cei.service.messages.MessageBase;
import cn.ma.cei.utils.Checker;
import com.alibaba.fastjson.JSONObject;
import io.vertx.core.http.ServerWebSocket;

/**
 *
 * @author u0151316
 */
public class WSConnection {

    private final ServerWebSocket ws;
    public final String clientID;


    public WSConnection(String clientID, ServerWebSocket ws) {
        this.ws = ws;
        this.clientID = clientID;
    }

    public void onTextMessage(String text) {
        System.err.println("[Client] Rec: " + text);
        WebsocketService.process(this, text);
    }



    public void send(JSONObject json) {
        String text = json.toJSONString();
        System.err.println("[Client] Send: " + text);
        ws.writeTextMessage(text);
    }

    public void send(String text) {

    }

    public void onClose() {

    }
}
