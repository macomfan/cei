/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.python3;

import cn.ma.cei.generator.*;
import cn.ma.cei.generator.builder.*;
import cn.ma.cei.generator.buildin.*;
import cn.ma.cei.generator.langs.python3.tools.Python3Class;
import cn.ma.cei.generator.langs.python3.tools.Python3File;
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
    private Python3Class authenticationClass;
    

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
        BuilderContext.setupBuildInVariableType(xDecimal.typeName, "Decimal", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(TheArray.typeName, "list", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(RestfulRequest.typeName, "RestfulRequest", "from impl.restfulrequest import RestfulRequest");
        BuilderContext.setupBuildInVariableType(RestfulResponse.typeName, "RestfulResponse", "from impl.restfulresponse import RestfulResponse");
        BuilderContext.setupBuildInVariableType(RestfulConnection.typeName, "RestfulConnection", "from impl.restfulconnection import RestfulConnection");
        BuilderContext.setupBuildInVariableType(RestfulOptions.typeName, "RestfulOptions", "from impl.restfuloptions import RestfulOptions");
        BuilderContext.setupBuildInVariableType(JsonWrapper.typeName, "JsonWrapper", "from impl.jsonwrapper import JsonWrapper");
        BuilderContext.setupBuildInVariableType(AuthenticationTool.typeName, "AuthenticationTool", "from impl.authenticationtool import AuthenticationTool");
        BuilderContext.setupBuildInVariableType(TheStream.typeName, "byte[]", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(JsonChecker.typeName, "JsonChecker", "from impl.jsonchecker import JsonChecker");

        CEIPath workingFolder = BuilderContext.getWorkingFolder();
        CEIPath exchangeFolder = CEIPath.appendPath(workingFolder, "exchanges");
        exchangeFolder.mkdirs();
        BuilderContext.setExchangeFolder(exchangeFolder);

        mainFile = new Python3File(exchangeName);
        authenticationClass = new Python3Class("Authentication");
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
    public IModelBuilder createModelBuilder() {
        return new Python3ModelBuilder(mainFile);
    }

    @Override
    public void endExchange() {
        mainFile.addInnerClass(authenticationClass);
        mainFile.build(BuilderContext.getExchangeFolder());
        
    }

    @Override
    public IAuthenticationBuilder createAuthenticationBuilder() {
        return new Python3AuthenticationBuilder(authenticationClass);
    }

}
