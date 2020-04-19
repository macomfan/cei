/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.service;

import cn.ma.cei.exception.CEIErrors;
import io.vertx.core.http.HttpServer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author u0151316
 */
public class WebSocketService {

    enum Result {
        IGNORE,
        ERROR,
        SUCCESSFUL,
    }

    static public class MessageResult {
        private static final MessageResult error = new MessageResult(null, Result.ERROR);
        private static final MessageResult ignore = new MessageResult(null, Result.IGNORE);

        public static MessageResult error() {
            return error;
        }

        public static MessageResult normal(WebSocketMessage message) {
            return new MessageResult(message, Result. SUCCESSFUL);
        }

        public static MessageResult ignore() {
            return ignore;
        }


        private MessageResult(WebSocketMessage message, Result result) {
            this.message = message;
            this.result = result;
        }

        public WebSocketMessage message;
        public Result result;
    }

    private final Map<String, WebSocketClient> clientMap = new HashMap<>();
    private final Map<String, IWebSocketHandler> channelHandlerMap = new HashMap<>();

    public void registerHandler(String channel, IWebSocketHandler channelHandler) {
        channelHandlerMap.put(channel, channelHandler);
    }

//    public static void registerProcessor(ProcessorBase processor) {
//        if (Checker.isEmpty(processor.getCatalog()) || Checker.isEmpty(processor.getType())) {
//            System.err.println("[WS] Processor incorrect");
//        }
//        if (processor.getType().equalsIgnoreCase(ProcessorBase.REQ)) {
//            reqProcessorMap.put(processor.getCatalog().toLowerCase(), processor);
//        }
//    }

//    private static void processReq(WebSocketClient connection, JSONObject json) {
//        String msgType = json.getString("msg");
//        long requestID = json.getLong("id");
//        msgType = msgType.toLowerCase();
//        ProcessContext context = new ProcessContext();
//        context.clientID = connection.clientID;
//        context.connection = connection;
//        context.requestID = requestID;
//        if (Checker.isEmpty(msgType)) {
//            System.err.println("[WS] Error unknown message type");
//            context.error("Error unknown message type");
//            return;
//        }
//        if (!reqProcessorMap.containsKey(msgType)) {
//            System.err.println("[WS] Cannot find the processor for " + msgType);
//            context.error("Error unknown message type");
//            return;
//        }
//        reqProcessorMap.get(msgType).invoke(context, json.getJSONObject("param"));
//    }
//
//    public static void process(WebSocketClient connection, String text) {
//        JSONObject json = JSONObject.parseObject(text);
//        String type = json.getString("type");
//        if (Checker.isEmpty(type)) {
//            System.err.println("[Client] Error unknown request type");
//        }
//        switch (type) {
//            case ProcessorBase.REQ:
//                processReq(connection, json);
//                break;
//            case ProcessorBase.SUB:
//                break;
//        }
//    }

    public void startService(HttpServer server) {
        server.websocketHandler(webSocket -> {
            IWebSocketHandler handler = channelHandlerMap.getOrDefault(webSocket.path(), null);
            if (handler == null) {
                webSocket.reject();
                return;
            }
            String clientID = webSocket.binaryHandlerID();
            WebSocketClient connection = new WebSocketClient(clientID, webSocket);
            System.out.println("[WS] Connected: " + clientID);
            if (clientMap.containsKey(clientID)) {
                System.out.println("[WS] Error: duplicte client connection.");
                webSocket.reject();
                return;
            }
            clientMap.put(clientID, connection);

            webSocket.frameHandler(frameHandler -> {
                MessageResult result;
                if (frameHandler.isText()) {
                    result = handler.handle(frameHandler.textData(), null);
                } else if (frameHandler.isBinary()) {
                    result = handler.handle(null, frameHandler.binaryData());
                } else {
                    webSocket.close();
                    return;
                }
                if (result == null) {
                    CEIErrors.showCodeFailure(this.getClass(), "Processor error");
                }
                switch (result.result) {
                    case IGNORE:
                        break;
                    case ERROR:
                        webSocket.close();
                        return;
                    case SUCCESSFUL:
                        if (result.message != null && result.message.getProcessor() != null) {
                            result.message.getProcessor().process(result.message, connection);
                        }
                }
            });

            webSocket.closeHandler(closeHandler -> {
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
