/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.golang;

import cn.ma.cei.generator.*;
import cn.ma.cei.generator.builder.IExchangeBuilder;
import cn.ma.cei.generator.builder.IModelBuilder;
import cn.ma.cei.generator.builder.IRestfulClientBuilder;
import cn.ma.cei.generator.builder.ISignatureBuilder;
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

        Constant.signatureMethod().tryPut(SignatureTool.Constant.ASC, "signature.ASC");
        Constant.signatureMethod().tryPut(SignatureTool.Constant.DSC, "signature.DSC");
        Constant.signatureMethod().tryPut(SignatureTool.Constant.HOST, "signature.HOST");
        Constant.signatureMethod().tryPut(SignatureTool.Constant.METHOD, "signature.METHOD");
        Constant.signatureMethod().tryPut(SignatureTool.Constant.TARGET, "signature.TARGET");
        Constant.signatureMethod().tryPut(SignatureTool.Constant.UPPERCASE, "signature.UPPERCASE");
        Constant.signatureMethod().tryPut(SignatureTool.Constant.LOWERCASE, "signature.LOWERCASE");
        Constant.signatureMethod().tryPut(SignatureTool.Constant.NONE, "signature.NONE");

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
        BuilderContext.setupBuildInVariableType(SignatureTool.typeName, "signature", "ceiimpl");
        BuilderContext.setupBuildInVariableType(JsonChecker.typeName, "ceiimpl.JsonChecker", "ceiimpl");

        CEIPath workingFolder = BuilderContext.getWorkingFolder();
        CEIPath exchangeFolder = CEIPath.appendPath(workingFolder, "src", "exchanges");
        exchangeFolder.mkdirs();
        BuilderContext.setExchangeFolder(exchangeFolder);

        mainFile = new GoFile(exchangeName, exchangeName);
    }

    @Override
    public IRestfulClientBuilder getRestfulClientBuilder(VariableType clientType) {
        return new GoRestfulClientBuilder(clientType, mainFile);
    }

    @Override
    public ISignatureBuilder getSignatureBuilder() {
        return new GoSignatureBuilder(mainFile);
    }

    @Override
    public IModelBuilder getModelBuilder() {
        return new GoModelBuilder(mainFile);
    }

    @Override
    public void endExchange() {
        mainFile.build(BuilderContext.getExchangeFolder());
    }

}
