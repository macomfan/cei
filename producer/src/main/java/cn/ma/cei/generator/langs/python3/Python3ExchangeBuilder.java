/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.python3;

import cn.ma.cei.generator.CEIPath;
import cn.ma.cei.generator.builder.ExchangeBuilder;
import cn.ma.cei.generator.builder.ModelBuilder;
import cn.ma.cei.generator.builder.RestfulClientBuilder;
import cn.ma.cei.generator.builder.SignatureBuilder;
import cn.ma.cei.generator.buildin.*;
import cn.ma.cei.generator.environment.Constant;
import cn.ma.cei.generator.environment.Environment;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableFactory;
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
public class Python3ExchangeBuilder extends ExchangeBuilder {

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

        VariableFactory.setupBuildinVariableType(xString.typeName, "String", VariableFactory.NO_REF);
        VariableFactory.setupBuildinVariableType(xBoolean.typeName, "Boolean", VariableFactory.NO_REF);
        VariableFactory.setupBuildinVariableType(xInt.typeName, "Long", VariableFactory.NO_REF);
        VariableFactory.setupBuildinVariableType(xDecimal.typeName, "Decimal", VariableFactory.NO_REF);
        VariableFactory.setupBuildinVariableType(TheArray.typeName, "list", VariableFactory.NO_REF);
        VariableFactory.setupBuildinVariableType(RestfulRequest.typeName, "RestfulRequest", "from impl.restfulrequest import RestfulRequest");
        VariableFactory.setupBuildinVariableType(RestfulResponse.typeName, "RestfulResponse", "from impl.restfulresponse import RestfulResponse");
        VariableFactory.setupBuildinVariableType(RestfulConnection.typeName, "RestfulConnection", "from impl.restfulconnection import RestfulConnection");
        VariableFactory.setupBuildinVariableType(RestfulOptions.typeName, "RestfulOptions", "from impl.restfuloptions import RestfulOptions");
        VariableFactory.setupBuildinVariableType(JsonWrapper.typeName, "JsonWrapper", "from impl.jsonwrapper import JsonWrapper");
        VariableFactory.setupBuildinVariableType(SignatureTool.typeName, "SignatureTool", "from impl.signaturetool import SignatureTool");
        VariableFactory.setupBuildinVariableType(TheStream.typeName, "byte[]", VariableFactory.NO_REF);
        VariableFactory.setupBuildinVariableType(JsonChecker.typeName, "JsonChecker", "from impl.jsonchecker import JsonChecker");

        CEIPath workingFolder = Environment.getWorkingFolder();
        CEIPath exchangeFolder = CEIPath.appendPath(workingFolder, "exchanges");
        exchangeFolder.mkdirs();
        Environment.setExchangeFolder(exchangeFolder);

        mainFile = new Python3File(exchangeName);
        signatureClass = new Python3Class("Signature");
    }

    @Override
    public RestfulClientBuilder getRestfulClientBuilder() {
        return new Python3RestfulClientBuilder(mainFile);
    }

    @Override
    public ModelBuilder getModelBuilder() {
        return new Python3ModelBuilder(mainFile);
    }

    @Override
    public void endExchange() {
        mainFile.addInnerClass(signatureClass);
        mainFile.build(Environment.getExchangeFolder());
        
    }

    @Override
    public SignatureBuilder getSignatureBuilder() {
        return new Python3SignatureBuilder(signatureClass);
    }

}
