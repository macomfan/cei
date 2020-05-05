package cn.ma.cei.generator;

import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IWebSocketClientBuilder;
import cn.ma.cei.generator.builder.IWebSocketEventBuilder;
import cn.ma.cei.generator.builder.IWebSocketInterfaceBuilder;
import cn.ma.cei.generator.buildin.WebSocketCallback;
import cn.ma.cei.model.websocket.xWSInterface;
import cn.ma.cei.model.websocket.xWSUserCallback;
import cn.ma.cei.utils.Checker;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BuildWebSocketInterface {
    public static void build(xWSInterface intf, IWebSocketClientBuilder builder) {
        IWebSocketInterfaceBuilder interfaceBuilder = Checker.checkNull(builder.createWebSocketInterfaceBuilder(), builder, "WebSocketInterfaceBuilder");
        sMethod interfaceMethod = GlobalContext.getCurrentMethod();
        // Build input
        List<Variable> inputVariableList = new LinkedList<>();
        if (intf.inputList != null) {
            intf.inputList.forEach((input) -> input.doBuild(() -> {
                Variable inputVariable = interfaceMethod.createInputVariable(input.getType(), input.name);
                inputVariableList.add(inputVariable);
            }));
        }

        // Build events
        if (intf.events != null) {
            intf.events.forEach(callback -> {
                VariableType callbackType = GlobalContext.variableType(WebSocketCallback.typeName, getCallbackMessageType(callback));
                Variable callbackParam = interfaceMethod.createInputVariable(callbackType, callback.callback);
                inputVariableList.add(callbackParam);
            });
        }

        interfaceBuilder.startMethod(null, interfaceMethod.getDescriptor(), inputVariableList);
        // Build callback
        if (intf.events != null) {
            intf.events.forEach(event -> {
                VariableType callbackMessageType = getCallbackMessageType(event);
                IWebSocketEventBuilder eventBuilder = Checker.checkNull(interfaceBuilder.createWebSocketEventBuilder(), interfaceBuilder, "WebSocketEventBuilder");
                BuildWebSocketEvent.build(event, callbackMessageType, eventBuilder);
            });
        }

        Variable connection = interfaceMethod.getVariable("connection");
        // Build message
        if (intf.message != null) {
            IDataProcessorBuilder dataProcessorBuilder = Checker.checkNull(interfaceBuilder.createDataProcessorBuilder(), builder, "DataProcessorBuilder");
            BuildDataProcessor.Context context = new BuildDataProcessor.Context();
            context.procedure = intf.message;
            context.defaultInput = connection;
            context.dataProcessorBuilder = dataProcessorBuilder;
            BuildDataProcessor.build(context);
        }
        interfaceBuilder.endMethod();
    }

    private static VariableType getCallbackMessageType(xWSUserCallback callback) {
        if (callback.handler == null) {
            return null;
        }
        AtomicReference<VariableType> returnType = new AtomicReference<>();
        callback.handler.doBuild(() -> {
            returnType.set(BuildResponse.getReturnType(callback.handler));
            GlobalContext.getCurrentMethod().setReturnType(returnType.get());
        });
        return returnType.get();
    }
}
