/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.golang;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.CEIPath;
import cn.ma.cei.generator.Constant;
import cn.ma.cei.generator.builder.*;
import cn.ma.cei.generator.buildin.*;
import cn.ma.cei.langs.golang.tools.GoFile;
import cn.ma.cei.model.types.xBoolean;
import cn.ma.cei.model.types.xDecimal;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.model.types.xString;

/**
 * @author U0151316
 */
public class GoExchangeBuilder implements IExchangeBuilder {

    private GoFile mainFile;

    @Override
    public void startExchange(String exchangeName) {
        Constant.requestMethod().tryPut(RestfulRequest.RequestMethod.GET, "impl.GET");
        Constant.requestMethod().tryPut(RestfulRequest.RequestMethod.POST, "impl.POST");

        Constant.requestInfo().tryPut(CEIUtils.Constant.ASC, "impl.ASC");
        Constant.requestInfo().tryPut(CEIUtils.Constant.DSC, "impl.DSC");
        Constant.requestInfo().tryPut(CEIUtils.Constant.HOST, "impl.HOST");
        Constant.requestInfo().tryPut(CEIUtils.Constant.METHOD, "impl.METHOD");
        Constant.requestInfo().tryPut(CEIUtils.Constant.TARGET, "impl.TARGET");
        Constant.requestInfo().tryPut(CEIUtils.Constant.UPPERCASE, "impl.UPPERCASE");
        Constant.requestInfo().tryPut(CEIUtils.Constant.LOWERCASE, "impl.LOWERCASE");
        Constant.requestInfo().tryPut(CEIUtils.Constant.POSTBODY, "impl.POSTBODY");
        Constant.requestInfo().tryPut(CEIUtils.Constant.NONE, "impl.NONE");

        Constant.keyword().tryPut(Keyword.TRUE, "true");
        Constant.keyword().tryPut(Keyword.FALSE, "false");

        BuilderContext.setupBuildInVariableType(xString.typeName, "string", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(xBoolean.typeName, "bool", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(xInt.typeName, "int64", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(xDecimal.typeName, "float64", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(TheArray.typeName, "[]", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(TheStream.typeName, "[]byte", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(RestfulRequest.typeName, "impl.RestfulRequest", "../../impl");
        BuilderContext.setupBuildInVariableType(RestfulResponse.typeName, "impl.RestfulResponse", "../../impl");
        BuilderContext.setupBuildInVariableType(RestfulConnection.typeName, "impl.RestfulConnection", "../../impl");
        BuilderContext.setupBuildInVariableType(RestfulOptions.typeName, "impl.RestfulOptions", "../../impl");
        BuilderContext.setupBuildInVariableType(JsonWrapper.typeName, "impl.JsonWrapper", "../../impl");
        BuilderContext.setupBuildInVariableType(StringWrapper.typeName, "impl.StringWrapper", "../../impl");
        BuilderContext.setupBuildInVariableType(CEIUtils.typeName, "impl.CEIUtils", "../../impl");
        BuilderContext.setupBuildInVariableType(JsonChecker.typeName, "impl.JsonChecker", "../../impl");
        BuilderContext.setupBuildInVariableType(Procedures.typeName, "impl.Procedures", BuilderContext.NO_REF);

        BuilderContext.setupBuildInVariableType(WebSocketConnection.typeName, "impl.WebSocketConnection", "../../impl");
        BuilderContext.setupBuildInVariableType(WebSocketEvent.typeName, "impl.WebSocketEvent", "../../impl");
        BuilderContext.setupBuildInVariableType(WebSocketMessage.typeName, "impl.WebSocketMessage", "../../impl");
        BuilderContext.setupBuildInVariableType(WebSocketCallback.typeName, "impl.WebSocketCallback", "../../impl");
        BuilderContext.setupBuildInVariableType(WebSocketOptions.typeName, "impl.WebSocketOptions", "../../impl");

        CEIPath workingFolder = BuilderContext.getWorkingFolder();
        CEIPath exchangeFolder = CEIPath.appendPath(workingFolder, "src", "exchanges", exchangeName);
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
        return new GoWebSocketClientBuilder(mainFile);
    }

    @Override
    public IMethodBuilder createFunctionBuilder() {
        return new GoFunctionBuilder(mainFile);
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
