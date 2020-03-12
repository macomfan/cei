package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.IWebSocketClientBuilder;
import cn.ma.cei.generator.buildin.WebSocketOptions;
import cn.ma.cei.model.websocket.xWebSocket;

public class BuildWebSocketClient {
    public static void build(xWebSocket client, IWebSocketClientBuilder builder) {
        if (builder == null) {
            throw new CEIException("WebSocketClientBuilder is null");
        }
        WebSocketOptions options = new WebSocketOptions();
        if (client.connection.timeout != null) {
            options.connectionTimeout = client.connection.timeout;
        }
        if (client.connection != null) {
            options.url = client.connection.url.target;
        }
        builder.startClient(GlobalContext.getCurrentModel(), options);

        // Build connection
        sMethod connectMethod = GlobalContext.getCurrentModel().createMethod("connect");
        GlobalContext.setCurrentMethod(connectMethod);
        BuildWebSocketConnection.build(client.connection, client.actions, builder.createWebSocketInterfaceBuilder());
        GlobalContext.setCurrentMethod(null);

        if (client.interfaces != null) {
            client.interfaces.forEach((intf) -> intf.doBuild(() -> {
                sMethod method = GlobalContext.getCurrentModel().createMethod(intf.name);
                GlobalContext.setCurrentMethod(method);
                BuildWebSocketInterface.build(intf, builder.createWebSocketInterfaceBuilder());
                GlobalContext.setCurrentMethod(null);
            }));
        }

        builder.endClient();
    }
}
