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
import cn.ma.cei.generator.buildin.JsonWrapper;
import cn.ma.cei.generator.buildin.RestfulConnection;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.buildin.RestfulRequest;
import cn.ma.cei.generator.buildin.RestfulResponse;
import cn.ma.cei.generator.buildin.SignatureTool;
import cn.ma.cei.generator.buildin.TheStream;
import cn.ma.cei.generator.environment.Constant;
import cn.ma.cei.generator.environment.Environment;
import cn.ma.cei.generator.environment.Reference;
import cn.ma.cei.generator.langs.java.tools.JavaClass;
import cn.ma.cei.generator.langs.python3.tools.Python3Class;
import cn.ma.cei.model.types.xBoolean;
import cn.ma.cei.model.types.xDecimal;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.model.types.xLong;
import cn.ma.cei.model.types.xString;

/**
 *
 * @author U0151316
 */
public class Python3ExchangeBuilder extends ExchangeBuilder {

    private Python3Class mainClass;

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

        Reference.setupBuildinVariableType(xString.typeName, "String", Python3Code.NO_REF);
        Reference.setupBuildinVariableType(xBoolean.typeName, "Boolean", Python3Code.NO_REF);
        Reference.setupBuildinVariableType(xInt.typeName, "Integer", Python3Code.NO_REF);
        Reference.setupBuildinVariableType(xLong.typeName, "Long", Python3Code.NO_REF);
        Reference.setupBuildinVariableType(xDecimal.typeName, "Decimal", Python3Code.NO_REF);
        Reference.setupBuildinVariableType("array", "List", Python3Code.NO_REF);
        Reference.setupBuildinVariableType(RestfulRequest.typeName, "RestfulRequest", "from impl.restfulrequest import RestfulRequest");
        Reference.setupBuildinVariableType(RestfulResponse.typeName, "RestfulResponse", "from impl.restfulresponse import RestfulResponse");
        Reference.setupBuildinVariableType(RestfulConnection.typeName, "RestfulConnection", "from impl.restfulconnection import RestfulConnection");
        Reference.setupBuildinVariableType(RestfulOptions.typeName, "RestfulOptions", "from impl.restfuloptions import RestfulOptions");
        Reference.setupBuildinVariableType(JsonWrapper.typeName, "JsonWrapper", "from impl.jsonwrapper import JsonWrapper");
        Reference.setupBuildinVariableType(SignatureTool.typeName, "SignatureTool", "from impl.signaturetool import SignatureTool");
        Reference.setupBuildinVariableType(TheStream.typeName, "byte[]", Python3Code.NO_REF);

        CEIPath workingFolder = Environment.getWorkingFolder();
        CEIPath exchangeFolder = CEIPath.appendPath(workingFolder, "exchanges");
        exchangeFolder.mkdirs();
        Environment.setExchangeFolder(exchangeFolder);

        mainClass = new Python3Class(exchangeName);
    }

    @Override
    public RestfulClientBuilder getRestfulClientBuilder() {
        return new Python3RestfulClientBuilder(mainClass);
    }

    @Override
    public ModelBuilder getModelBuilder() {
        return new Python3ModelBuilder(mainClass);
    }

    @Override
    public void endExchange() {
        mainClass.build(Environment.getExchangeFolder());
    }

    @Override
    public SignatureBuilder getSignatureBuilder() {
        return new Python3SignatureBuilder(mainClass);
    }

}
