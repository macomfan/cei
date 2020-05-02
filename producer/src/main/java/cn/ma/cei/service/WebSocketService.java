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
import java.util.concurrent.ConcurrentHashMap;

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
        private static final MessageResult error = new MessageResult(null, null, Result.ERROR);
        private static final MessageResult ignore = new MessageResult(null, null, Result.IGNORE);

        public static MessageResult error() {
            return error;
        }

        public static MessageResult normal(WebSocketMessage message, WebSocketMessageProcessor processor) {
            return new MessageResult(message, processor, Result.SUCCESSFUL);
        }

        public static MessageResult ignore() {
            return ignore;
        }


        private MessageResult(WebSocketMessage message, WebSocketMessageProcessor processor, Result result) {
            this.message = message;
            this.result = result;
            this.processor = processor;
        }

        public WebSocketMessage message;
        public WebSocketMessageProcessor processor;
        public Result result;
    }

    static public class NotificationRegister {
        private WebSocketService service;

        boolean registerNotification(String notificationName, WebSocketClient client) {
            if (service.notificationMap.containsKey(notificationName)) {
                WebSocketNotification notification = service.notificationMap.get(notificationName);
                notification.registerClient(client);
                return true;
            } else {
                return false;
            }
        }

        boolean unregisterNotification(String notificationName, WebSocketClient client) {
            if (service.notificationMap.containsKey(notificationName)) {
                WebSocketNotification notification = service.notificationMap.get(notificationName);
                notification.unregisterClient(client);
                return true;
            } else {
                return false;
            }
        }

        void unregisterAll(WebSocketClient client) {
            service.notificationMap.forEachValue(1, item -> {
                item.unregisterClient(client);
            });
        }
    }

    private final ConcurrentHashMap<String, WebSocketClient> clientMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, WebSocketNotification> notificationMap = new ConcurrentHashMap<>();
    private final Map<String, IWebSocketHandler> channelHandlerMap = new HashMap<>();
    private final NotificationRegister notificationRegister = new NotificationRegister();

    public void registerHandler(String channel, IWebSocketHandler channelHandler) {
        channelHandlerMap.put(channel, channelHandler);
    }

    public void registerNotification(String notificationName, WebSocketNotification notification) {
        notificationMap.put(notificationName, notification);
    }

    public void startService(HttpServer server) {
        notificationRegister.service = this;

        server.websocketHandler(webSocket -> {
            IWebSocketHandler handler = channelHandlerMap.getOrDefault(webSocket.path(), null);
            if (handler == null) {
                System.out.println(String.format("Client connected and rejected by path %s", webSocket.path()));
                webSocket.reject();
                return;
            }

            String clientID = webSocket.binaryHandlerID();
            WebSocketClient connection = new WebSocketClient(clientID, webSocket, notificationRegister);
            System.out.println("[WS] Connected: " + clientID);
            if (clientMap.containsKey(clientID)) {
                System.out.println("[WS] Error: duplicate client connection.");
                webSocket.reject();
                return;
            }
            //Accept client
            clientMap.put(clientID, connection);
            connection.registerNotification("ping");

            webSocket.frameHandler(frameHandler -> {
                MessageResult result;
                if (frameHandler.isText()) {
                    result = handler.handle(frameHandler.textData(), null, connection);
                } else if (frameHandler.isBinary()) {
                    result = handler.handle(null, frameHandler.binaryData(), connection);
                } else {
                    webSocket.close();
                    return;
                }
                if (result == null) {
                    CEIErrors.showCodeFailure(this.getClass(), "Processor error");
                    return;
                }
                switch (result.result) {
                    case IGNORE:
                        break;
                    case ERROR:
                        System.err.println("Closing client");
                        webSocket.close();
                        return;
                    case SUCCESSFUL:
                        if (result.message != null && result.processor != null) {
                            result.processor.process(result.message, connection);
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
