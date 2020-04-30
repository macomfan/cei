package cn.ma.cei.generator;

import cn.ma.cei.generator.builder.IExchangeBuilder;
import cn.ma.cei.generator.buildin.Procedures;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.model.xSDK;
import cn.ma.cei.utils.Checker;

public class BuildExchange {

    public static void build(xSDK sdk, IExchangeBuilder builder) {
        builder.startExchange(sdk.name);
        RestfulOptions.registryMember();

        if (sdk.modelList != null) {
            sdk.modelList.forEach((model) -> model.doBuild(() -> {
                BuildModel.build(model, Checker.checkNull(builder.createModelBuilder(), builder, "ModelBuilder"));
            }));
        }

        VariableType procedureModel = GlobalContext.variableType(Procedures.typeName);
        if (sdk.procedures != null) {
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

        // Build restful interfaces
        if (sdk.clients != null) {
            if (sdk.clients.restfulList != null) {
                sdk.clients.restfulList.forEach((restful) -> restful.doBuild(() -> {
                    GlobalContext.setupRunTimeVariableType(restful.name, BuilderContext.NO_REF);
                    VariableType clientType = GlobalContext.variableType(restful.name);
                    GlobalContext.setCurrentModel(clientType);
                    BuildRestfulInterfaceClient.build(restful, Checker.checkNull(builder.createRestfulClientBuilder(), builder, "RestfulClientBuilder"));
                    GlobalContext.setCurrentModel(null);
                }));
            }

            // Build web socket interfaces
            if (sdk.clients.webSocketList != null) {
                sdk.clients.webSocketList.forEach((websocket) -> websocket.doBuild(() -> {
                    GlobalContext.setupRunTimeVariableType(websocket.name, BuilderContext.NO_REF);
                    VariableType clientType = GlobalContext.variableType(websocket.name);
                    GlobalContext.setCurrentModel(clientType);
                    BuildWebSocketClient.build(websocket, Checker.checkNull(builder.createWebSocketClientBuilder(), builder, "WebSocketClientBuilder"));
                    GlobalContext.setCurrentModel(null);
                }));
            }
        }
        builder.endExchange();
    }
}
