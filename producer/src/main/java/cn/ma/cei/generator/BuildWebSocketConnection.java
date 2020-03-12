package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.IWebSocketActionBuilder;
import cn.ma.cei.generator.builder.IWebSocketInterfaceBuilder;
import cn.ma.cei.generator.buildin.WebSocketAction;
import cn.ma.cei.generator.buildin.WebSocketConnection;
import cn.ma.cei.generator.buildin.WebSocketOptions;
import cn.ma.cei.model.websocket.xAction;
import cn.ma.cei.model.websocket.xConnection;

import java.util.LinkedList;
import java.util.List;

public class BuildWebSocketConnection {
    public static void build(xConnection connection, List<xAction> actions, IWebSocketInterfaceBuilder builder) {
        if (builder == null) {
            throw new CEIException("Builder is null");
        }

        sMethod connectMethod = GlobalContext.getCurrentMethod();
        Variable option = connectMethod.createInputVariable(WebSocketOptions.getType(), "option");

        // Build input
        List<Variable> inputVariableList = new LinkedList<>();
        if (connection.inputList != null) {
            connection.inputList.forEach((input) -> input.doBuild(() -> {
                Variable inputVariable = connectMethod.createInputVariable(input.getType(), input.name);
                inputVariableList.add(inputVariable);
            }));
        }

        builder.onAddReference(WebSocketConnection.getType());
        builder.startMethod(null, connectMethod.getDescriptor(), inputVariableList);
        {
            // Actions
            if (actions != null) {
                actions.forEach((action) -> action.doBuild(() -> {
                    BuildWebSocketAction.build(action, builder.createWebSocketActionBuilder());
                }));
            }

            if (connection.onConnect.send != null) {
                sMethod onConnect = connectMethod.createNestedMethod("onConnect");
                GlobalContext.setCurrentMethod(onConnect);
                Variable connectionVariable = onConnect.createInputVariable(WebSocketConnection.getType(), "connection");
                BuildWebSocketImplementation.buildSendInAction(connection.onConnect.send, connectionVariable, builder.createOnConnectBuilder());
                builder.setupOnConnect(onConnect);
                GlobalContext.setCurrentMethod(connectMethod);
            }

            // Connect
            Variable url = BuildAttributeExtension.createValueFromAttribute("target", connection.url, builder);
            // TODO
            // if url is not string, convert to string
            builder.connect(url, option);
        }
        builder.endMethod();
    }
}
