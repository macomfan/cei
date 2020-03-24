package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.*;
import cn.ma.cei.generator.builder.*;
import cn.ma.cei.generator.buildin.*;
import cn.ma.cei.generator.langs.java.buildin.TheLinkedList;
import cn.ma.cei.generator.langs.java.tools.JavaClass;
import cn.ma.cei.model.types.xBoolean;
import cn.ma.cei.model.types.xDecimal;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.model.types.xString;

public class JavaExchangeBuilder implements IExchangeBuilder {

    private JavaClass mainClass;
    private JavaClass authenticationClass;

    @Override
    public void startExchange(String exchangeName) {

        Constant.requestMethod().tryPut(RestfulRequest.RequestMethod.GET, "RestfulRequest.Method.GET");
        Constant.requestMethod().tryPut(RestfulRequest.RequestMethod.POST, "RestfulRequest.Method.POST");

        Constant.authenticationMethod().tryPut(AuthenticationTool.Constant.ASC, "AuthenticationTool.Constant.ASC");
        Constant.authenticationMethod().tryPut(AuthenticationTool.Constant.DSC, "AuthenticationTool.Constant.DSC");
        Constant.authenticationMethod().tryPut(AuthenticationTool.Constant.HOST, "AuthenticationTool.Constant.HOST");
        Constant.authenticationMethod().tryPut(AuthenticationTool.Constant.METHOD, "AuthenticationTool.Constant.METHOD");
        Constant.authenticationMethod().tryPut(AuthenticationTool.Constant.TARGET, "AuthenticationTool.Constant.TARGET");
        Constant.authenticationMethod().tryPut(AuthenticationTool.Constant.UPPERCASE, "AuthenticationTool.Constant.UPPERCASE");
        Constant.authenticationMethod().tryPut(AuthenticationTool.Constant.LOWERCASE, "AuthenticationTool.Constant.LOWERCASE");
        Constant.authenticationMethod().tryPut(AuthenticationTool.Constant.NONE, "AuthenticationTool.Constant.NONE");

        BuilderContext.setupBuildInVariableType(xString.typeName, "String", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(xBoolean.typeName, "Boolean", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(xInt.typeName, "Long", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(xDecimal.typeName, "BigDecimal", "java.math.BigDecimal");
        BuilderContext.setupBuildInVariableType(TheArray.typeName, "List", "java.util.List");
        BuilderContext.setupBuildInVariableType(TheLinkedList.typeName, "LinkedList", "java.util.LinkedList");
        BuilderContext.setupBuildInVariableType(RestfulRequest.typeName, "RestfulRequest", "cn.ma.cei.impl.RestfulRequest");
        BuilderContext.setupBuildInVariableType(RestfulResponse.typeName, "RestfulResponse", "cn.ma.cei.impl.RestfulResponse");
        BuilderContext.setupBuildInVariableType(RestfulConnection.typeName, "RestfulConnection", "cn.ma.cei.impl.RestfulConnection");
        BuilderContext.setupBuildInVariableType(RestfulOptions.typeName, "RestfulOptions", "cn.ma.cei.impl.RestfulOptions");
        BuilderContext.setupBuildInVariableType(JsonWrapper.typeName, "JsonWrapper", "cn.ma.cei.impl.JsonWrapper");
        BuilderContext.setupBuildInVariableType(AuthenticationTool.typeName, "AuthenticationTool", "cn.ma.cei.impl.AuthenticationTool");
        BuilderContext.setupBuildInVariableType(TheStream.typeName, "byte[]", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(JsonChecker.typeName, "JsonChecker", "cn.ma.cei.impl.JsonChecker");
        BuilderContext.setupBuildInVariableType(StringWrapper.typeName, "StringWrapper", "cn.ma.cei.impl.StringWrapper");

        BuilderContext.setupBuildInVariableType(WebSocketConnection.typeName, "WebSocketConnection", "cn.ma.cei.impl.WebSocketConnection");
        BuilderContext.setupBuildInVariableType(WebSocketAction.typeName, "WebSocketAction", "cn.ma.cei.impl.WebSocketAction");
        BuilderContext.setupBuildInVariableType(WebSocketMessage.typeName, "WebSocketMessage", "cn.ma.cei.impl.WebSocketMessage");
        BuilderContext.setupBuildInVariableType(WebSocketCallback.typeName, "IWebSocketCallback", "cn.ma.cei.impl.IWebSocketCallback");
        BuilderContext.setupBuildInVariableType(WebSocketOptions.typeName, "WebSocketOptions", "cn.ma.cei.impl.WebSocketOptions");

        CEIPath workingFolder = BuilderContext.getWorkingFolder();
        CEIPath exchangeFolder = CEIPath.appendPath(workingFolder, "src", "main", "java", "cn", "ma", "cei", "exchanges");
        exchangeFolder.mkdirs();
        BuilderContext.setExchangeFolder(exchangeFolder);

        mainClass = new JavaClass(exchangeName, "cn.ma.cei.exchanges");
        authenticationClass = new JavaClass("Authentication");
    }

    @Override
    public IRestfulClientBuilder createRestfulClientBuilder() {
        return new JavaRestfulClientBuilder(mainClass);
    }

    @Override
    public IWebSocketClientBuilder createWebSocketClientBuilder() {
        return new JavaWebSocketClientBuilder(mainClass);
    }

    @Override
    public IModelBuilder createModelBuilder() {
        return new JavaModelBuilder(mainClass);
    }

    @Override
    public IAuthenticationBuilder createAuthenticationBuilder() {
        return new JavaAuthenticationBuilder(authenticationClass);
    }

    @Override
    public void endExchange() {
        if (authenticationClass != null) {
            mainClass.addInnerClass(authenticationClass);
        }
        mainClass.build(BuilderContext.getExchangeFolder());
    }
}
