/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.golang;

import cn.ma.cei.generator.*;
import cn.ma.cei.generator.builder.*;
import cn.ma.cei.generator.buildin.*;
import cn.ma.cei.generator.langs.golang.tools.GoFile;
import cn.ma.cei.model.types.xBoolean;
import cn.ma.cei.model.types.xDecimal;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.model.types.xString;

/**
 *
 * @author U0151316
 */
public class GoExchangeBuilder implements IExchangeBuilder {

    private GoFile mainFile;
    
    @Override
    public void startExchange(String exchangeName) {
        Constant.requestMethod().tryPut(RestfulRequest.RequestMethod.GET, "ceiimpl.GET");
        Constant.requestMethod().tryPut(RestfulRequest.RequestMethod.POST, "ceiimpl.POST");

        Constant.authenticationMethod().tryPut(AuthenticationTool.Constant.ASC, "authentication.ASC");
        Constant.authenticationMethod().tryPut(AuthenticationTool.Constant.DSC, "authentication.DSC");
        Constant.authenticationMethod().tryPut(AuthenticationTool.Constant.HOST, "authentication.HOST");
        Constant.authenticationMethod().tryPut(AuthenticationTool.Constant.METHOD, "authentication.METHOD");
        Constant.authenticationMethod().tryPut(AuthenticationTool.Constant.TARGET, "authentication.TARGET");
        Constant.authenticationMethod().tryPut(AuthenticationTool.Constant.UPPERCASE, "authentication.UPPERCASE");
        Constant.authenticationMethod().tryPut(AuthenticationTool.Constant.LOWERCASE, "authentication.LOWERCASE");
        Constant.authenticationMethod().tryPut(AuthenticationTool.Constant.NONE, "authentication.NONE");

        BuilderContext.setupBuildInVariableType(xString.typeName, "string", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(xBoolean.typeName, "bool", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(xInt.typeName, "int64", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(xDecimal.typeName, "float64", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(TheArray.typeName, "[]", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(TheStream.typeName, "[]byte", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(RestfulRequest.typeName, "ceiimpl.RestfulRequest", "ceiimpl");
        BuilderContext.setupBuildInVariableType(RestfulResponse.typeName, "RestfulResponse", "ceiimpl");
        BuilderContext.setupBuildInVariableType(RestfulConnection.typeName, "ceiimpl", "ceiimpl");
        BuilderContext.setupBuildInVariableType(RestfulOptions.typeName, "ceiimpl.RestfulOptions", "ceiimpl");
        BuilderContext.setupBuildInVariableType(JsonWrapper.typeName, "ceiimpl.JsonWrapper", "ceiimpl");
        BuilderContext.setupBuildInVariableType(AuthenticationTool.typeName, "authentication", "ceiimpl");
        BuilderContext.setupBuildInVariableType(JsonChecker.typeName, "ceiimpl.JsonChecker", "ceiimpl");

        CEIPath workingFolder = BuilderContext.getWorkingFolder();
        CEIPath exchangeFolder = CEIPath.appendPath(workingFolder, "src", "exchanges");
        exchangeFolder.mkdirs();
        BuilderContext.setExchangeFolder(exchangeFolder);

        mainFile = new GoFile(exchangeName, exchangeName);
    }

    @Override
    public IRestfulClientBuilder createRestfulClientBuilder() {
        return new GoRestfulClientBuilder(mainFile);
    }

    @Override
    public IWebSocketClientBuilder createWebSocketClientBuilder() {
        return null;
    }

    @Override
    public IAuthenticationBuilder createAuthenticationBuilder() {
        return new GoAuthenticationBuilder(mainFile);
    }

    @Override
    public IModelBuilder createModelBuilder() {
        return new GoModelBuilder(mainFile);
    }

    @Override
    public void endExchange() {
        mainFile.build(BuilderContext.getExchangeFolder());
    }

}
