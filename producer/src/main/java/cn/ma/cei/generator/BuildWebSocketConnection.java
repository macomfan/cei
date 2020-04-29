package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IWebSocketClientBuilder;
import cn.ma.cei.generator.builder.IWebSocketNestedBuilder;
import cn.ma.cei.generator.builder.IWebSocketInterfaceBuilder;
import cn.ma.cei.generator.buildin.WebSocketCallback;
import cn.ma.cei.generator.buildin.WebSocketConnection;
import cn.ma.cei.model.base.xDataProcessorItem;
import cn.ma.cei.model.processor.xWebSocketCallback;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.model.websocket.*;
import cn.ma.cei.model.xProcedure;
import cn.ma.cei.utils.Checker;

import java.util.LinkedList;
import java.util.List;

public class BuildWebSocketConnection {
    public static void build(xWSConnection connection, List<xWSOnMessage> events, IWebSocketClientBuilder builder) {
        buildOpen(connection.open, events, builder);
        buildClose(connection.close, builder);
    }

    private static void buildOpen(xWSOpen open, List<xWSOnMessage> events, IWebSocketClientBuilder builder) {
        if (open == null) {
            CEIErrors.showXMLFailure("open in WebSocket client must be defined");
            return;
        }

        IWebSocketInterfaceBuilder interfaceBuilder = Checker.checkNull(builder.createWebSocketInterfaceBuilder(), builder, "WebSocketInterfaceBuilder");
        sMethod openMethod = GlobalContext.getCurrentModel().createMethod("open");
        GlobalContext.setCurrentMethod(openMethod);
        Variable option = openMethod.getVariable("option");
        Variable connection = openMethod.getVariable("connection");
        // Build input
        List<Variable> inputVariableList = new LinkedList<>();
        if (open.inputList != null) {
            open.inputList.forEach((input) -> input.doBuild(() -> {
                Variable inputVariable = openMethod.createInputVariable(input.getType(), input.name);
                inputVariableList.add(inputVariable);
            }));
            if (open.onConnect != null && !Checker.isEmpty(open.onConnect.callback)) {
                VariableType callbackType = GlobalContext.variableType(WebSocketCallback.typeName, WebSocketConnection.getType());
                inputVariableList.add(openMethod.createInputVariable(callbackType, open.onConnect.callback));
            }
        }

        interfaceBuilder.startMethod(null, openMethod.getDescriptor(), inputVariableList);
        {
            // Build system events
            if (!Checker.isNull(events)) {
                events.forEach((event) -> event.doBuild(() -> {
                    BuildWebSocketEvent.build(event,
                            Checker.checkNull(interfaceBuilder.createWebSocketEventBuilder(), builder, "WebSocketEventBuilder"));
                }));
            }

            // Build OnConnect
            IWebSocketNestedBuilder onConnectBuilder =
                    Checker.checkNull(interfaceBuilder.createOnConnectBuilder(), interfaceBuilder, "OnConnectBuilder");
            sMethod callbackMethod = buildSystemCallback(openMethod, "onConnect", open.onConnect, onConnectBuilder);
            if (callbackMethod != null) {
                interfaceBuilder.setupOnConnect(connection, callbackMethod);
            }

            // Build pre processor
            IDataProcessorBuilder dataProcessorBuilder = Checker.checkNull(interfaceBuilder.createDataProcessorBuilder(), builder, "DataProcessorBuilder");
            if (open.connect.preProcessor != null && !Checker.isNull(open.connect.preProcessor.items)) {
                BuildDataProcessor.Context context = new BuildDataProcessor.Context();
                context.dataProcessorBuilder = dataProcessorBuilder;
                context.procedure = open.connect.preProcessor;
                BuildDataProcessor.build(context);
            }
            // Connect
            if (!Checker.isEmpty(open.connect.target)) {
                Variable target = GlobalContext.getCurrentMethod().queryVariableOrConstant(open.connect.target, xString.inst.getType(), dataProcessorBuilder);
                interfaceBuilder.connect(connection, target, option);
            } else {
                interfaceBuilder.connect(connection, GlobalContext.createStringConstant(""), option);
            }

        }
        interfaceBuilder.endMethod();
        GlobalContext.setCurrentMethod(null);
    }

    private static void buildClose(xWSClose close, IWebSocketClientBuilder builder) {
        if (close == null) {
            return;
        }
        IWebSocketInterfaceBuilder interfaceBuilder = Checker.checkNull(builder.createWebSocketInterfaceBuilder(), builder, "WebSocketInterfaceBuilder");
        sMethod closeMethod = GlobalContext.getCurrentModel().createMethod("close");
        GlobalContext.setCurrentMethod(closeMethod);
        Variable connection = closeMethod.getVariable("connection");
        Variable option = closeMethod.getVariable("option");
        List<Variable> inputVariableList = new LinkedList<>();
        if (close.onClose != null && !Checker.isEmpty(close.onClose.callback)) {
            VariableType callbackType = GlobalContext.variableType(WebSocketCallback.typeName, WebSocketConnection.getType());
            inputVariableList.add(closeMethod.createInputVariable(callbackType, close.onClose.callback));
        }

        interfaceBuilder.startMethod(null, closeMethod.getDescriptor(), inputVariableList);
        {
            // Build OnClose
            IWebSocketNestedBuilder onCloseBuilder =
                    Checker.checkNull(interfaceBuilder.createOnCloseBuilder(), interfaceBuilder, "OnConnectBuilder");
            sMethod callbackMethod = buildSystemCallback(closeMethod, "onClose", close.onClose, onCloseBuilder);
            if (callbackMethod != null) {
                interfaceBuilder.setupOnClose(connection, callbackMethod);
            }
            // Close
            interfaceBuilder.close(connection, option);
        }
        interfaceBuilder.endMethod();
        GlobalContext.setCurrentMethod(null);
    }

    private static sMethod buildSystemCallback(sMethod eventMethod, String systemCallbackName, xWSSystemCallback callback, IWebSocketNestedBuilder builder) {
        if (callback == null) {
            return null;
        }

        sMethod callbackMethod = eventMethod.createNestedMethod(systemCallbackName);
        GlobalContext.setCurrentMethod(callbackMethod);
        Variable connectionVariable = callbackMethod.createInputVariable(WebSocketConnection.getType(), "connection");
        IDataProcessorBuilder dataProcessorBuilder = Checker.checkNull(builder.createDataProcessorBuilder(), builder, "DataProcessorBuilder");
        boolean foundCallbackInvoking = false;
        if (!Checker.isNull(callback.items)) {
            for (xDataProcessorItem item : callback.items) {
                if (item instanceof xWebSocketCallback) {
                    if (!Checker.isEmpty(callback.callback)) {
                        ((xWebSocketCallback)item).func = "{" + callback.callback + "}";
                    }
                    foundCallbackInvoking = true;
                }
            }
            BuildDataProcessor.Context context = new BuildDataProcessor.Context();
            context.procedure = callback;
            context.defaultInput = connectionVariable;
            context.dataProcessorBuilder = dataProcessorBuilder;
            BuildDataProcessor.build(context);
        }
        if (!foundCallbackInvoking && !Checker.isEmpty(callback.callback)) {
            xProcedure procedure = new xProcedure();
            procedure.items = new LinkedList<>();
            xWebSocketCallback newCallback = new xWebSocketCallback();
            newCallback.func = "{" + callback.callback + "}";
            procedure.items.add(newCallback);
            BuildDataProcessor.Context context = new BuildDataProcessor.Context();
            context.procedure = procedure;
            context.defaultInput = connectionVariable;
            context.dataProcessorBuilder = dataProcessorBuilder;
            BuildDataProcessor.build(context);
        }
        GlobalContext.setCurrentMethod(eventMethod);
        return callbackMethod;
    }
}
