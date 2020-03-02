/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.python3;

import cn.ma.cei.generator.*;
import cn.ma.cei.generator.builder.IExchangeBuilder;
import cn.ma.cei.generator.builder.IModelBuilder;
import cn.ma.cei.generator.builder.IRestfulClientBuilder;
import cn.ma.cei.generator.builder.ISignatureBuilder;
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
    private Python3Class signatureClass;
    

    @Override
    public void startExchange(String exchangeName) {
        Constant.requestMethod().tryPut(RestfulRequest.RequestMethod.GET, "RestfulRequest.Method.GET");
        Constant.requestMethod().tryPut(RestfulRequest.RequestMethod.POST, "RestfulRequest.Method.POST");

        Constant.signatureMethod().tryPut(SignatureTool.Constant.ASC, "SignatureTool.Constant.ASC");
        Constant.signatureMethod().tryPut(SignatureTool.Constant.DSC, "SignatureTool.Constant.DSC");
        Constant.signatureMethod().tryPut(SignatureTool.Constant.HOST, "SignatureTool.Constant.HOST");
        Constant.signatureMethod().tryPut(SignatureTool.Constant.METHOD, "SignatureTool.Constant.METHOD");
        Constant.signatureMethod().tryPut(SignatureTool.Constant.TARGET, "SignatureTool.Constant.TARGET");
        Constant.signatureMethod().tryPut(SignatureTool.Constant.UPPERCASE, "SignatureTool.Constant.UPPERCASE");
        Constant.signatureMethod().tryPut(SignatureTool.Constant.LOWERCASE, "SignatureTool.Constant.LOWERCASE");
        Constant.signatureMethod().tryPut(SignatureTool.Constant.NONE, "SignatureTool.Constant.NONE");

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
        BuilderContext.setupBuildInVariableType(SignatureTool.typeName, "SignatureTool", "from impl.signaturetool import SignatureTool");
        BuilderContext.setupBuildInVariableType(TheStream.typeName, "byte[]", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(JsonChecker.typeName, "JsonChecker", "from impl.jsonchecker import JsonChecker");

        CEIPath workingFolder = BuilderContext.getWorkingFolder();
        CEIPath exchangeFolder = CEIPath.appendPath(workingFolder, "exchanges");
        exchangeFolder.mkdirs();
        BuilderContext.setExchangeFolder(exchangeFolder);

        mainFile = new Python3File(exchangeName);
        signatureClass = new Python3Class("Signature");
    }

    @Override
    public IRestfulClientBuilder getRestfulClientBuilder(VariableType clientType) {
        return new Python3RestfulClientBuilder(mainFile);
    }

    @Override
    public IModelBuilder getModelBuilder() {
        return new Python3ModelBuilder(mainFile);
    }

    @Override
    public void endExchange() {
        mainFile.addInnerClass(signatureClass);
        mainFile.build(BuilderContext.getExchangeFolder());
        
    }

    @Override
    public ISignatureBuilder getSignatureBuilder() {
        return new Python3SignatureBuilder(signatureClass);
    }

}
