/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.service;

import cn.ma.cei.service.messages.InitMessage;
import cn.ma.cei.service.processors.IProcessor;
import cn.ma.cei.service.processors.ProcessorBase;
import cn.ma.cei.utils.Checker;
import com.alibaba.fastjson.JSONObject;
import io.vertx.core.http.HttpServer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author u0151316
 */
public class WebsocketService {

    private final static Map<String, ProcessorBase> reqProcessorMap = new HashMap<>();
    private final Map<String, WSConnection> clientMap = new HashMap<>();

    public static void registerProcessor(ProcessorBase processor) {
        if (Checker.isEmpty(processor.getCatalog()) || Checker.isEmpty(processor.getType())) {
            System.err.println("[WS] Processor incorrect");
        }
        if (processor.getType().equalsIgnoreCase(ProcessorBase.REQ)) {
            reqProcessorMap.put(processor.getCatalog().toLowerCase(), processor);
        }
    }

    private static void processReq(WSConnection connection, JSONObject json) {
        String msgType = json.getString("msg");
        long requestID = json.getLong("id");
        msgType = msgType.toLowerCase();
        ProcessContext context = new ProcessContext();
        context.clientID = connection.clientID;
        context.connection = connection;
        context.requestID = requestID;
        if (Checker.isEmpty(msgType)) {
            System.err.println("[WS] Error unknown message type");
            context.error("Error unknown message type");
            return;
        }
        if (!reqProcessorMap.containsKey(msgType)) {
            System.err.println("[WS] Cannot find the processor for " + msgType);
            context.error("Error unknown message type");
            return;
        }
        reqProcessorMap.get(msgType).invoke(context, json.getJSONObject("param"));
    }

    public static void process(WSConnection connection, String text) {
        JSONObject json = JSONObject.parseObject(text);
        String type = json.getString("type");
        if (Checker.isEmpty(type)) {
            System.err.println("[Client] Error unknown request type");
        }
        switch (type) {
            case ProcessorBase.REQ:
                processReq(connection, json);
                break;
            case ProcessorBase.SUB:
                break;
        }
    }

    public void startService(HttpServer server) {
        server.websocketHandler(websocket -> {
            String clientID = websocket.binaryHandlerID();
            WSConnection connection = new WSConnection(clientID, websocket);
            System.out.println("[WS] Connected: " + clientID);
            if (clientMap.containsKey(clientID)) {
                System.out.println("[WS] Error: duplicte client connection.");
                return;
            }
            clientMap.put(clientID, connection);

            websocket.frameHandler(handler -> {
                if (handler.isText()) {
                    connection.onTextMessage(handler.textData());
                }
            });

            websocket.closeHandler(handler -> {
                connection.onClose();
                if (!clientMap.containsKey(clientID)) {
                    System.out.println("[WS] Error: Client lost.");
                    return;
                }
                clientMap.remove(clientID);
                System.out.println("[WS] Disconnected: " + clientID);
            });
        });
    }
}
