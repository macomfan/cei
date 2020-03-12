package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.IWebSocketImplementationBuilder;
import cn.ma.cei.generator.builder.IWebSocketInterfaceBuilder;
import cn.ma.cei.generator.buildin.WebSocketAction;
import cn.ma.cei.generator.buildin.WebSocketCallback;
import cn.ma.cei.generator.buildin.WebSocketMessage;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.model.websocket.xCallback;
import cn.ma.cei.model.websocket.xInterface;
import cn.ma.cei.model.websocket.xSend;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BuildWebSocketInterface {
    public static void build(xInterface intf, IWebSocketInterfaceBuilder builder) {
        if (builder == null) {
            throw new CEIException("WebSocketInterfaceBuilder is null");
        }
        sMethod interfaceMethod = GlobalContext.getCurrentMethod();
        // Build input
        List<Variable> inputVariableList = new LinkedList<>();
        if (intf.inputList != null) {
            intf.inputList.forEach((input) -> input.doBuild(() -> {
                Variable inputVariable = interfaceMethod.createInputVariable(input.getType(), input.name);
                inputVariableList.add(inputVariable);
            }));
        }

        if (intf.callbacks != null) {
            intf.callbacks.forEach(callback -> {
                VariableType callbackType = GlobalContext.variableType(WebSocketCallback.typeName, getCallbackMessageType(callback));
                Variable callbackParam = interfaceMethod.createInputVariable(callbackType, callback.name);
                inputVariableList.add(callbackParam);
            });
        }

        builder.startMethod(null, interfaceMethod.getDescriptor(), inputVariableList);
        // Build callback
        if (intf.callbacks != null) {
            intf.callbacks.forEach(callback -> {
                BuildWebSocketAction.build(callback, builder.createWebSocketActionBuilder());
            });
        }
        // Build send
        if (intf.send != null) {
            Variable sendVariable = BuildAttributeExtension.createValueFromAttribute("value", intf.send, builder);
            builder.send(sendVariable);
        }
        builder.endMethod();
    }

    private static VariableType getCallbackMessageType(xCallback callback) {
        if (callback.response == null) {
            return null;
        }
        AtomicReference<VariableType> returnType = new AtomicReference<>();
        callback.response.doBuild(() -> {
            returnType.set(BuildResponse.getReturnType(callback.response));
            GlobalContext.getCurrentMethod().setReturnType(returnType.get());
        });
        return returnType.get();
    }
}
