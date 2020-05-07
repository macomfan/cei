package cn.ma.cei.generator;

import cn.ma.cei.generator.builder.IWebSocketClientBuilder;
import cn.ma.cei.generator.buildin.WebSocketConnection;
import cn.ma.cei.generator.buildin.WebSocketOptions;
import cn.ma.cei.model.websocket.xWebSocket;

public class BuildWebSocketClient {
    public static void build(xWebSocket client, IWebSocketClientBuilder builder) {

        GlobalContext.getCurrentModel().addPrivateMember(WebSocketOptions.getType(), "option");
        GlobalContext.getCurrentModel().addPrivateMember(WebSocketConnection.getType(), "connection");

        WebSocketOptions option = new WebSocketOptions();
        if (client.connection.timeout != null) {
            option.connectionTimeout = client.connection.timeout;
        }
        option.url = client.connection.url;
        VariableType clientModel = GlobalContext.getCurrentModel();
        sMethod defaultConstructor = clientModel.createMethod(clientModel.getName() + "DefaultConstructor");

        builder.startClient(GlobalContext.getCurrentModel(), option,
                defaultConstructor.getVariable("connection"), defaultConstructor.getVariable("option"));

        // Build connection and events
        BuildWebSocketConnection.build(client.connection, client.events, builder);

        // Build interfaces
        if (client.interfaces != null) {
            client.interfaces.forEach((intf) -> intf.doBuild(() -> {
                sMethod method = GlobalContext.getCurrentModel().createMethod(intf.name);
                GlobalContext.setCurrentMethod(method);
                BuildWebSocketInterface.build(intf, builder);
                GlobalContext.setCurrentMethod(null);
            }));
        }

        builder.endClient();
    }
}
