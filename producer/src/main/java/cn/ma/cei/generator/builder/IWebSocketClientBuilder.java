package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.IMethod;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.buildin.WebSocketOptions;

public interface IWebSocketClientBuilder extends IBuilderBase {
    void startClient(VariableType client, WebSocketOptions option, Variable connectionVariable, Variable optionVariable);

    IWebSocketInterfaceBuilder createWebSocketInterfaceBuilder();

    // IWebSocketConnectionBuilder createWebSocketConnectionBuilder();

    void endClient();
}
