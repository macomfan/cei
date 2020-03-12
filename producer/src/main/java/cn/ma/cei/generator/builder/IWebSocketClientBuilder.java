package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.buildin.WebSocketOptions;

public interface IWebSocketClientBuilder {
    void startClient(VariableType client, WebSocketOptions options);

    IWebSocketInterfaceBuilder createWebSocketInterfaceBuilder();

    // IWebSocketConnectionBuilder createWebSocketConnectionBuilder();

    void endClient();
}
