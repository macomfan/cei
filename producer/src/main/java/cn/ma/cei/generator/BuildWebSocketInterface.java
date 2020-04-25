package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.IWebSocketInterfaceBuilder;
import cn.ma.cei.generator.buildin.WebSocketCallback;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.model.websocket.xCallback;
import cn.ma.cei.model.websocket.xWSInterface;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BuildWebSocketInterface {
    public static void build(xWSInterface intf, IWebSocketInterfaceBuilder builder) {
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
                VariableType callbackMessageType = getCallbackMessageType(callback);
                BuildWebSocketAction.build(callback, callbackMessageType, builder.createWebSocketActionBuilder());
            });
        }
        // Build send
        if (intf.send != null) {
            BuildDataProcessor.Context context = new BuildDataProcessor.Context();
            context.procedure = intf.send;
            context.returnVariableName = intf.send.value;
            context.specifiedReturnType = xString.inst.getType();
            context.methodBuilder = builder;
            //Variable sendVariable = BuildUserProcedure.createValueFromProcedure(xString.inst.getType(), intf.send.value, intf.send, builder);
            Variable sendVariable = BuildDataProcessor.build(context);
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
