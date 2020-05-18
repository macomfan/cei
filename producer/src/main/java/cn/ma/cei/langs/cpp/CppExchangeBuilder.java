package cn.ma.cei.langs.cpp;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.CEIPath;
import cn.ma.cei.generator.Constant;
import cn.ma.cei.generator.builder.*;
import cn.ma.cei.generator.buildin.*;
import cn.ma.cei.langs.cpp.tools.CppClass;
import cn.ma.cei.langs.cpp.tools.CppExchangeFile;
import cn.ma.cei.model.types.xBoolean;
import cn.ma.cei.model.types.xDecimal;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.model.types.xString;

public class CppExchangeBuilder implements IExchangeBuilder {
    
    private String exchangeName;
    private CppExchangeFile mainFile = null;
    
    @Override
    public void startExchange(String exchangeName) {
        this.exchangeName = exchangeName;

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

        BuilderContext.setupBuildInVariableType(xString.typeName, "std::string", "<string>");
        BuilderContext.setupBuildInVariableType(xBoolean.typeName, "bool", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(xInt.typeName, "long", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(xDecimal.typeName, "Decimal", "\"impl/decimal.h\"");
        BuilderContext.setupBuildInVariableType(TheArray.typeName, "std::vector", "<vector>");
        BuilderContext.setupBuildInVariableType(RestfulRequest.typeName, "RestfulRequest", "\"impl/RestfulRequest.h\"");
        BuilderContext.setupBuildInVariableType(RestfulResponse.typeName, "RestfulResponse", "\"impl/RestfulResponse.h\"");
        BuilderContext.setupBuildInVariableType(RestfulConnection.typeName, "RestfulConnection", "\"impl/RestfulConnection.h\"");
        BuilderContext.setupBuildInVariableType(RestfulOptions.typeName, "RestfulOptions", "\"impl/RestfulOptions.h\"");
        BuilderContext.setupBuildInVariableType(JsonWrapper.typeName, "JsonWrapper", "\"impl/RestfulJsonWrapper.h\"");
        BuilderContext.setupBuildInVariableType(CEIUtils.typeName, "CEIUtils", "\"impl/CEIUtils.h\"");
        BuilderContext.setupBuildInVariableType(TheStream.typeName, "byte[]", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(JsonChecker.typeName, "JsonChecker", "\"impl/JsonChecker.h\"");
        BuilderContext.setupBuildInVariableType(StringWrapper.typeName, "StringWrapper", "\"impl/StringWrapper.h\"");
        BuilderContext.setupBuildInVariableType(Procedures.typeName, "Procedures", BuilderContext.NO_REF);

        BuilderContext.setupBuildInVariableType(WebSocketConnection.typeName, "WebSocketConnection", "\"impl/WebSocketConnection.h\"");
        BuilderContext.setupBuildInVariableType(WebSocketEvent.typeName, "WebSocketEvent", "\"impl/WebSocketEvent.h\"");
        BuilderContext.setupBuildInVariableType(WebSocketMessage.typeName, "WebSocketMessage", "\"impl/WebSocketMessage.h\"");
        BuilderContext.setupBuildInVariableType(WebSocketCallback.typeName, "WebSocketCallback", "\"impl/WebSocketCallback.h\"");
        BuilderContext.setupBuildInVariableType(WebSocketOptions.typeName, "WebSocketOptions", "\"impl/WebSocketOptions.h\"");

        CEIPath workingFolder = BuilderContext.getWorkingFolder();
        CEIPath exchangeIncludeFolder = CEIPath.appendPath(workingFolder, "include", "exchanges");
        exchangeIncludeFolder.mkdirs();
        CEIPath exchangeSrcFolder = CEIPath.appendPath(workingFolder, "src", "exchanges");
        exchangeSrcFolder.mkdirs();

        mainFile = new CppExchangeFile(exchangeName, exchangeSrcFolder, exchangeIncludeFolder);
    }

    @Override
    public IRestfulClientBuilder createRestfulClientBuilder() {
        return new CppRestfulClientBuilder(mainFile);
    }

    @Override
    public IWebSocketClientBuilder createWebSocketClientBuilder() {
        return new CppWebSocketClientBuilder(mainFile);
    }

    @Override
    public IMethodBuilder createFunctionBuilder() {
        CppClass functionClass = new CppClass(Procedures.getType().getDescriptor());
        return new CppFunctionBuilder(functionClass);
    }

    @Override
    public IModelBuilder createModelBuilder() {
        return new CppModelBuilder(mainFile);
    }

    @Override
    public void endExchange() {
        mainFile.build();
    }
}
