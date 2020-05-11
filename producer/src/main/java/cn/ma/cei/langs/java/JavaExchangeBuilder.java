package cn.ma.cei.langs.java;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.CEIPath;
import cn.ma.cei.generator.Constant;
import cn.ma.cei.generator.builder.*;
import cn.ma.cei.generator.buildin.*;
import cn.ma.cei.langs.java.buildin.TheLinkedList;
import cn.ma.cei.langs.java.tools.JavaClass;
import cn.ma.cei.model.types.xBoolean;
import cn.ma.cei.model.types.xDecimal;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.model.types.xString;

public class JavaExchangeBuilder implements IExchangeBuilder {

    private JavaClass mainClass;
    private JavaClass functionClass;

    @Override
    public void startExchange(String exchangeName) {

        Constant.requestMethod().tryPut(RestfulRequest.RequestMethod.GET, "RestfulRequest.Method.GET");
        Constant.requestMethod().tryPut(RestfulRequest.RequestMethod.POST, "RestfulRequest.Method.POST");

        Constant.requestInfo().tryPut(CEIUtils.Constant.ASC, "CEIUtils.Constant.ASC");
        Constant.requestInfo().tryPut(CEIUtils.Constant.DSC, "CEIUtils.Constant.DSC");
        Constant.requestInfo().tryPut(CEIUtils.Constant.HOST, "CEIUtils.Constant.HOST");
        Constant.requestInfo().tryPut(CEIUtils.Constant.METHOD, "CEIUtils.Constant.METHOD");
        Constant.requestInfo().tryPut(CEIUtils.Constant.TARGET, "CEIUtils.Constant.TARGET");
        Constant.requestInfo().tryPut(CEIUtils.Constant.UPPERCASE, "CEIUtils.Constant.UPPERCASE");
        Constant.requestInfo().tryPut(CEIUtils.Constant.LOWERCASE, "CEIUtils.Constant.LOWERCASE");
        Constant.requestInfo().tryPut(CEIUtils.Constant.POSTBODY, "CEIUtils.Constant.POSTBODY");
        Constant.requestInfo().tryPut(CEIUtils.Constant.NONE, "CEIUtils.Constant.NONE");

        Constant.keyword().tryPut(Keyword.TRUE, "true");
        Constant.keyword().tryPut(Keyword.FALSE, "false");

        BuilderContext.setupBuildInVariableType(xString.typeName, "String", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(xBoolean.typeName, "Boolean", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(xInt.typeName, "Long", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(xDecimal.typeName, "BigDecimal", "java.math.BigDecimal");
        BuilderContext.setupBuildInVariableType(TheArray.typeName, "List", "java.util.List");
        BuilderContext.setupBuildInVariableType(TheLinkedList.typeName, "LinkedList", "java.util.LinkedList");
        BuilderContext.setupBuildInVariableType(RestfulRequest.typeName, "RestfulRequest", "cei.impl.RestfulRequest");
        BuilderContext.setupBuildInVariableType(RestfulResponse.typeName, "RestfulResponse", "cei.impl.RestfulResponse");
        BuilderContext.setupBuildInVariableType(RestfulConnection.typeName, "RestfulConnection", "cei.impl.RestfulConnection");
        BuilderContext.setupBuildInVariableType(RestfulOptions.typeName, "RestfulOptions", "cei.impl.RestfulOptions");
        BuilderContext.setupBuildInVariableType(JsonWrapper.typeName, "JsonWrapper", "cei.impl.JsonWrapper");
        BuilderContext.setupBuildInVariableType(CEIUtils.typeName, "CEIUtils", "cei.impl.CEIUtils");
        BuilderContext.setupBuildInVariableType(TheStream.typeName, "byte[]", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(JsonChecker.typeName, "JsonChecker", "cei.impl.JsonChecker");
        BuilderContext.setupBuildInVariableType(StringWrapper.typeName, "StringWrapper", "cei.impl.StringWrapper");
        BuilderContext.setupBuildInVariableType(Procedures.typeName, "Procedures", BuilderContext.NO_REF);

        BuilderContext.setupBuildInVariableType(WebSocketConnection.typeName, "WebSocketConnection", "cei.impl.WebSocketConnection");
        BuilderContext.setupBuildInVariableType(WebSocketEvent.typeName, "WebSocketEvent", "cei.impl.WebSocketEvent");
        BuilderContext.setupBuildInVariableType(WebSocketMessage.typeName, "WebSocketMessage", "cei.impl.WebSocketMessage");
        BuilderContext.setupBuildInVariableType(WebSocketCallback.typeName, "IWebSocketCallback", "cei.impl.IWebSocketCallback");
        BuilderContext.setupBuildInVariableType(WebSocketOptions.typeName, "WebSocketOptions", "cei.impl.WebSocketOptions");

        CEIPath workingFolder = BuilderContext.getWorkingFolder();
        CEIPath exchangeFolder = CEIPath.appendPath(workingFolder, "src", "main", "java", "cei", "exchanges");
        exchangeFolder.mkdirs();
        BuilderContext.setExchangeFolder(exchangeFolder);

        mainClass = new JavaClass(exchangeName, "cei.exchanges");

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
    public IMethodBuilder createFunctionBuilder() {
        functionClass = new JavaClass(Procedures.getType().getDescriptor());
        return new JavaFunctionBuilder(functionClass);
    }

    @Override
    public IModelBuilder createModelBuilder() {
        return new JavaModelBuilder(mainClass);
    }

    @Override
    public void endExchange() {
        if (functionClass != null) {
            mainClass.addInnerClass(functionClass);
        }
        mainClass.build(BuilderContext.getExchangeFolder());
    }
}
