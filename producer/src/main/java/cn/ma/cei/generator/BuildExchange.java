package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.generator.builder.IExchangeBuilder;
import cn.ma.cei.generator.buildin.CEIUtils;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.buildin.WebSocketConnection;
import cn.ma.cei.generator.buildin.WebSocketOptions;
import cn.ma.cei.model.xSDK;
import cn.ma.cei.utils.Checker;

public class BuildExchange {

    public static void build(xSDK sdk, IExchangeBuilder builder) {
        builder.startExchange(sdk.name);
        RestfulOptions.registryMember();

        if (sdk.modelList != null) {
            sdk.modelList.forEach((model) -> model.doBuild(() -> {
                BuildModel.build(model, Checker.checkBuilder(builder.createModelBuilder(), builder.getClass(), "ModelBuilder"));
            }));
        }

        // Build restful interfaces
        if (sdk.clients != null) {
            if (sdk.clients.restfulList != null) {
                sdk.clients.restfulList.forEach((restful) -> restful.doBuild(() -> {
                    GlobalContext.setupRunTimeVariableType(restful.name, BuilderContext.NO_REF);
                    VariableType clientType = GlobalContext.variableType(restful.name);
                    GlobalContext.setCurrentModel(clientType);
                    BuildRestfulInterfaceClient.build(restful, Checker.checkBuilder(builder.createRestfulClientBuilder(), builder.getClass(), "RestfulClientBuilder"));
                    GlobalContext.setCurrentModel(null);
                }));
            }

            // Build web socket interfaces
            if (sdk.clients.webSocketList != null) {
                sdk.clients.webSocketList.forEach((websocket) -> websocket.doBuild(() -> {
                    GlobalContext.setupRunTimeVariableType(websocket.name, BuilderContext.NO_REF);
                    VariableType clientType = GlobalContext.variableType(websocket.name);
                    clientType.addPrivateMember(WebSocketOptions.getType(), "option");
                    clientType.addPrivateMember(WebSocketConnection.getType(), "connection");
                    GlobalContext.setCurrentModel(clientType);
                    BuildWebSocketClient.build(websocket, Checker.checkBuilder(builder.createWebSocketClientBuilder(), builder.getClass(), "WebSocketClientBuilder"));
                    GlobalContext.setCurrentModel(null);
                }));
            }
        }

        VariableType authenticationType = GlobalContext.variableType(CEIUtils.typeName);
        if (sdk.authentications != null) {
            GlobalContext.setCurrentModel(authenticationType);
            if (sdk.authentications.restfulList != null) {
                sdk.authentications.restfulList.forEach(authentication -> authentication.doBuild(() -> {
                    sMethod authenticationMethod = authenticationType.createMethod(authentication.name);
                    GlobalContext.setCurrentMethod(authenticationMethod);
                    BuildAuthentication.buildRestful(authentication, Checker.checkBuilder(builder.createAuthenticationBuilder(), builder.getClass(), "AuthenticationBuilder"));
                    GlobalContext.setCurrentMethod(null);
                }));
            }
            if (sdk.authentications.webSocketList != null) {
                sdk.authentications.webSocketList.forEach(authentication -> authentication.doBuild(() -> {
                    sMethod authenticationMethod = authenticationType.createMethod(authentication.name);
                    GlobalContext.setCurrentMethod(authenticationMethod);
                    BuildAuthentication.buildWebSocket(authentication, Checker.checkBuilder(builder.createAuthenticationBuilder(), builder.getClass(), "AuthenticationBuilder"));
                    GlobalContext.setCurrentMethod(null);
                }));
            }
            GlobalContext.setCurrentModel(null);
        }
        builder.endExchange();
    }
}
