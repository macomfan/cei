package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.IExchangeBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.buildin.SignatureTool;
import cn.ma.cei.model.xSDK;

public class BuildExchange {

    public static void build(xSDK sdk, IExchangeBuilder builder) {
        if (builder == null) {
            throw new CEIException("[BuildExchange] ExchangeBuilder is null");
        }

        builder.startExchange(sdk.name);
        RestfulOptions.registryMember();

        if (sdk.modelList != null) {
            sdk.modelList.forEach((model) -> model.doBuild(() -> {
                BuildModel.build(model, builder.createModelBuilder());
            }));
        }

        if (sdk.clients != null) {
            if (sdk.clients.restfulList != null) {
                sdk.clients.restfulList.forEach((restful) -> restful.doBuild(() -> {
                    GlobalContext.setupRunTimeVariableType(restful.name, BuilderContext.NO_REF);
                    VariableType clientType = GlobalContext.variableType(restful.name);
                    GlobalContext.setCurrentModel(clientType);
                    BuildRestfulInterfaceClient.build(restful, builder.createRestfulClientBuilder());
                    GlobalContext.setCurrentModel(null);
                }));
            }
            if (sdk.clients.webSocketList != null) {
                sdk.clients.webSocketList.forEach((websocket) -> websocket.doBuild(() -> {
                    GlobalContext.setupRunTimeVariableType(websocket.name, BuilderContext.NO_REF);
                    VariableType clientType = GlobalContext.variableType(websocket.name);
                    GlobalContext.setCurrentModel(clientType);
                    BuildWebSocketClient.build(websocket, builder.createWebSocketClientBuilder());
                    GlobalContext.setCurrentModel(null);
                }));
            }
        } else {
            // TODO
            // No clients here
        }

        VariableType signatureType = GlobalContext.variableType(SignatureTool.typeName);
        if (sdk.signatureList != null) {
            GlobalContext.setCurrentModel(signatureType);
            sdk.signatureList.forEach(signature -> signature.doBuild(() -> {
                sMethod signatureMethod = signatureType.createMethod(signature.name);
                GlobalContext.setCurrentMethod(signatureMethod);
                BuildSignature.build(signature, builder.createSignatureBuilder());
                GlobalContext.setCurrentMethod(null);
            }));
            GlobalContext.setCurrentModel(null);
        }
        builder.endExchange();
    }
}
