package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.IExchangeBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.buildin.AuthenticationTool;
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

        VariableType authenticationType = GlobalContext.variableType(AuthenticationTool.typeName);
        if (sdk.authentications != null) {
            GlobalContext.setCurrentModel(authenticationType);
            if (sdk.authentications.restfulList != null) {
                sdk.authentications.restfulList.forEach(authentication -> authentication.doBuild(() -> {
                    sMethod authenticationMethod = authenticationType.createMethod(authentication.name);
                    GlobalContext.setCurrentMethod(authenticationMethod);
                    BuildAuthentication.build(authentication, builder.createAuthenticationBuilder());
                    GlobalContext.setCurrentMethod(null);
                }));
            }
            if (sdk.authentications.webSocketList != null) {
                sdk.authentications.webSocketList.forEach(authentication -> authentication.doBuild(() -> {
                    sMethod authenticationMethod = authenticationType.createMethod(authentication.name);
                    GlobalContext.setCurrentMethod(authenticationMethod);
                    // BuildAuthentication.build(authentication, builder.createAuthenticationBuilder());
                    GlobalContext.setCurrentMethod(null);
                }));
            }

            GlobalContext.setCurrentModel(null);
        }
        builder.endExchange();
    }
}
