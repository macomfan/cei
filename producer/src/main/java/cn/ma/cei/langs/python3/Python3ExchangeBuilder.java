/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.python3;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.CEIPath;
import cn.ma.cei.generator.Constant;
import cn.ma.cei.generator.builder.*;
import cn.ma.cei.generator.buildin.*;
import cn.ma.cei.langs.python3.tools.Python3Class;
import cn.ma.cei.langs.python3.tools.Python3File;
import cn.ma.cei.model.types.xBoolean;
import cn.ma.cei.model.types.xDecimal;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.model.types.xString;

/**
 *
 * @author U0151316
 */
public class Python3ExchangeBuilder implements IExchangeBuilder {

    private Python3File mainFile;
    private Python3Class functionClass;
    

    @Override
    public void startExchange(String exchangeName) {
        Constant.requestMethod().tryPut(RestfulRequest.RequestMethod.GET, "RestfulRequest.Method.GET");
        Constant.requestMethod().tryPut(RestfulRequest.RequestMethod.POST, "RestfulRequest.Method.POST");

        Constant.authenticationMethod().tryPut(CEIUtils.Constant.ASC, "CEIUtils.Constant.ASC");
        Constant.authenticationMethod().tryPut(CEIUtils.Constant.DSC, "CEIUtils.Constant.DSC");
        Constant.authenticationMethod().tryPut(CEIUtils.Constant.HOST, "CEIUtils.Constant.HOST");
        Constant.authenticationMethod().tryPut(CEIUtils.Constant.METHOD, "CEIUtils.Constant.METHOD");
        Constant.authenticationMethod().tryPut(CEIUtils.Constant.TARGET, "CEIUtils.Constant.TARGET");
        Constant.authenticationMethod().tryPut(CEIUtils.Constant.UPPERCASE, "CEIUtils.Constant.UPPERCASE");
        Constant.authenticationMethod().tryPut(CEIUtils.Constant.LOWERCASE, "CEIUtils.Constant.LOWERCASE");
        Constant.authenticationMethod().tryPut(CEIUtils.Constant.NONE, "CEIUtils.Constant.NONE");

        BuilderContext.setupBuildInVariableType(xString.typeName, "String", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(xBoolean.typeName, "Boolean", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(xInt.typeName, "Long", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(xDecimal.typeName, "Decimal", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(TheArray.typeName, "list", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(RestfulRequest.typeName, "RestfulRequest", "from impl.restfulrequest import RestfulRequest");
        BuilderContext.setupBuildInVariableType(RestfulResponse.typeName, "RestfulResponse", "from impl.restfulresponse import RestfulResponse");
        BuilderContext.setupBuildInVariableType(RestfulConnection.typeName, "RestfulConnection", "from impl.restfulconnection import RestfulConnection");
        BuilderContext.setupBuildInVariableType(RestfulOptions.typeName, "RestfulOptions", "from impl.restfuloptions import RestfulOptions");
        BuilderContext.setupBuildInVariableType(JsonWrapper.typeName, "JsonWrapper", "from impl.jsonwrapper import JsonWrapper");
        BuilderContext.setupBuildInVariableType(CEIUtils.typeName, "CEIUtils", "from impl.ceiutils import CEIUtils");
        BuilderContext.setupBuildInVariableType(TheStream.typeName, "byte[]", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(JsonChecker.typeName, "JsonChecker", "from impl.jsonchecker import JsonChecker");
        BuilderContext.setupBuildInVariableType(StringWrapper.typeName, "StringWrapper", "from impl.stringwrapper import StringWrapper");
        BuilderContext.setupBuildInVariableType(Procedures.typeName, "Procedures", BuilderContext.NO_REF);

        BuilderContext.setupBuildInVariableType(WebSocketConnection.typeName, "WebSocketConnection", "from impl.websocketconnection import WebSocketConnection");
        BuilderContext.setupBuildInVariableType(WebSocketEvent.typeName, "WebSocketEvent", "from impl.websocketevent import WebSocketEvent");
        BuilderContext.setupBuildInVariableType(WebSocketMessage.typeName, "WebSocketMessage", "from impl.websocketmessage import WebSocketMessage");
        BuilderContext.setupBuildInVariableType(WebSocketCallback.typeName, "IWebSocketCallback", "from impl.websocketcallback import IWebSocketCallback");
        BuilderContext.setupBuildInVariableType(WebSocketOptions.typeName, "WebSocketOptions", "from impl.websocketoptions import WebSocketOptions");

        CEIPath workingFolder = BuilderContext.getWorkingFolder();
        CEIPath exchangeFolder = CEIPath.appendPath(workingFolder, "exchanges");
        exchangeFolder.mkdirs();
        BuilderContext.setExchangeFolder(exchangeFolder);

        mainFile = new Python3File(exchangeName);
        functionClass = new Python3Class("Authentication");
    }

    @Override
    public IRestfulClientBuilder createRestfulClientBuilder() {
        return new Python3RestfulClientBuilder(mainFile);
    }

    @Override
    public IWebSocketClientBuilder createWebSocketClientBuilder() {
        return new Python3WebSocketClientBuilder(mainFile);
    }

    @Override
    public IMethodBuilder createFunctionBuilder() {
        return new Python3FunctionBuilder(functionClass);
    }

    @Override
    public IModelBuilder createModelBuilder() {
        return new Python3ModelBuilder(mainFile);
    }

    @Override
    public void endExchange() {
        mainFile.addInnerClass(functionClass);
        mainFile.build(BuilderContext.getExchangeFolder());
        
    }
}
