package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.generator.builder.IExchangeBuilder;
import cn.ma.cei.generator.buildin.Procedures;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.buildin.WebSocketOptions;
import cn.ma.cei.model.xSDK;
import cn.ma.cei.utils.Checker;

public class BuildExchange {

    public static void build(xSDK sdk, IExchangeBuilder builder) {
        builder.startExchange(sdk.name);
        RestfulOptions.registryMember();
        WebSocketOptions.registryMember();

        if (!Checker.isNull(sdk.modelList)) {
            sdk.modelList.forEach((model) -> model.doBuild(() -> {
                BuildModel.build(model, Checker.checkNull(builder.createModelBuilder(), builder, "ModelBuilder"));
            }));
        } else {
            CEIErrors.showWarning("No any model defined for SDK: ", sdk.name);
        }

        if (sdk.procedures != null && !Checker.isNull(sdk.procedures.functions)) {
            VariableType procedureModel = GlobalContext.variableType(Procedures.typeName);
            GlobalContext.setCurrentModel(procedureModel);
            if (sdk.procedures.functions != null) {
                sdk.procedures.functions.forEach(function -> function.doBuild(() -> {
                    sMethod functionMethod = procedureModel.createMethod(function.name);
                    GlobalContext.setCurrentMethod(functionMethod);
                    BuildFunction.build(function, Checker.checkNull(builder.createFunctionBuilder(), builder, "FunctionBuilder"));
                    GlobalContext.setCurrentMethod(null);
                }));
            }
            GlobalContext.setCurrentModel(null);
        }

        if (sdk.clients != null) {
            // Build restful interfaces
            if (!Checker.isNull(sdk.clients.restfulList)) {
                sdk.clients.restfulList.forEach((restful) -> restful.doBuild(() -> {
                    GlobalContext.setupRunTimeVariableType(restful.name, BuilderContext.NO_REF);
                    VariableType clientType = GlobalContext.variableType(restful.name);
                    GlobalContext.setCurrentModel(clientType);
                    BuildRestfulInterfaceClient.build(restful, Checker.checkNull(builder.createRestfulClientBuilder(), builder, "RestfulClientBuilder"));
                    GlobalContext.setCurrentModel(null);
                }));
            }
            // Build web socket interfaces
            if (!Checker.isNull(sdk.clients.webSocketList)) {
                sdk.clients.webSocketList.forEach((websocket) -> websocket.doBuild(() -> {
                    GlobalContext.setupRunTimeVariableType(websocket.name, BuilderContext.NO_REF);
                    VariableType clientType = GlobalContext.variableType(websocket.name);
                    GlobalContext.setCurrentModel(clientType);
                    BuildWebSocketClient.build(websocket, Checker.checkNull(builder.createWebSocketClientBuilder(), builder, "WebSocketClientBuilder"));
                    GlobalContext.setCurrentModel(null);
                }));
            }
        } else {
            CEIErrors.showWarning("No any client defined for SDK: ", sdk.name);
        }
        builder.endExchange();
    }
}
