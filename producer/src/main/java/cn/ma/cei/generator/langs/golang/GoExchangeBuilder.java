/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.golang;

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
import cn.ma.cei.generator.buildin.TheArray;
import cn.ma.cei.generator.buildin.TheStream;
import cn.ma.cei.generator.environment.Constant;
import cn.ma.cei.generator.environment.Environment;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.langs.golang.tools.GoFile;
import cn.ma.cei.model.types.xBoolean;
import cn.ma.cei.model.types.xDecimal;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.model.types.xString;

/**
 *
 * @author U0151316
 */
public class GoExchangeBuilder extends ExchangeBuilder {

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

        VariableFactory.setupBuildinVariableType(xString.typeName, "string", VariableFactory.NO_REF);
        VariableFactory.setupBuildinVariableType(xBoolean.typeName, "bool", VariableFactory.NO_REF);
        VariableFactory.setupBuildinVariableType(xInt.typeName, "int64", VariableFactory.NO_REF);
        VariableFactory.setupBuildinVariableType(xDecimal.typeName, "float64", VariableFactory.NO_REF);
        VariableFactory.setupBuildinVariableType(TheArray.typeName, "[]", VariableFactory.NO_REF);
        VariableFactory.setupBuildinVariableType(TheStream.typeName, "[]byte", VariableFactory.NO_REF);
        VariableFactory.setupBuildinVariableType(RestfulRequest.typeName, "ceiimpl.RestfulRequest", "ceiimpl");
        VariableFactory.setupBuildinVariableType(RestfulResponse.typeName, "RestfulResponse", "ceiimpl");
        VariableFactory.setupBuildinVariableType(RestfulConnection.typeName, "ceiimpl", "ceiimpl");
        VariableFactory.setupBuildinVariableType(RestfulOptions.typeName, "ceiimpl.RestfulOptions", "ceiimpl");
        VariableFactory.setupBuildinVariableType(JsonWrapper.typeName, "ceiimpl.JsonWrapper", "ceiimpl");
        VariableFactory.setupBuildinVariableType(SignatureTool.typeName, "signature", "ceiimpl");

        CEIPath workingFolder = Environment.getWorkingFolder();
        CEIPath exchangeFolder = CEIPath.appendPath(workingFolder, "src", "exchanges");
        exchangeFolder.mkdirs();
        Environment.setExchangeFolder(exchangeFolder);

        mainFile = new GoFile(exchangeName, exchangeName);
    }

    @Override
    public RestfulClientBuilder getRestfulClientBuilder() {
        return new GoRestfulClientBuilder(mainFile);
    }

    @Override
    public SignatureBuilder getSignatureBuilder() {
        return new GoSignatureBuilder(mainFile);
    }

    @Override
    public ModelBuilder getModelBuilder() {
        return new GoModelBuilder(mainFile);
    }

    @Override
    public void endExchange() {
        mainFile.build(Environment.getExchangeFolder());
    }

}
