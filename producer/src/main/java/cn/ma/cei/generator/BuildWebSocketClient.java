package cn.ma.cei.generator;

import cn.ma.cei.generator.builder.IWebSocketClientBuilder;
import cn.ma.cei.generator.buildin.WebSocketConnection;
import cn.ma.cei.generator.buildin.WebSocketOptions;
import cn.ma.cei.model.websocket.xWebSocket;
import cn.ma.cei.utils.Checker;

public class BuildWebSocketClient {
    public static void build(xWebSocket client, IWebSocketClientBuilder builder) {

        GlobalContext.getCurrentModel().addPrivateMember(WebSocketOptions.getType(), "option");
        GlobalContext.getCurrentModel().addPrivateMember(WebSocketConnection.getType(), "connection");

        WebSocketOptions options = new WebSocketOptions();
        if (client.connection.timeout != null) {
            options.connectionTimeout = client.connection.timeout;
        }
        options.url = client.connection.url.target;
        builder.startClient(GlobalContext.getCurrentModel(), options);

        // Build connection
        sMethod connectMethod = GlobalContext.getCurrentModel().createMethod("connect");
        GlobalContext.setCurrentMethod(connectMethod);
        BuildWebSocketConnection.build(client.connection, client.actions,
                Checker.checkNull(builder.createWebSocketInterfaceBuilder(),builder, "WebSocketInterfaceBuilder"));
        GlobalContext.setCurrentMethod(null);

        // Build interfaces
        if (client.interfaces != null) {
            client.interfaces.forEach((intf) -> intf.doBuild(() -> {
                sMethod method = GlobalContext.getCurrentModel().createMethod(intf.name);
                GlobalContext.setCurrentMethod(method);
                BuildWebSocketInterface.build(intf,
                        Checker.checkNull(builder.createWebSocketInterfaceBuilder(), builder, "WebSocketInterfaceBuilder"));
                GlobalContext.setCurrentMethod(null);
            }));
        }

        builder.endClient();
    }
}
