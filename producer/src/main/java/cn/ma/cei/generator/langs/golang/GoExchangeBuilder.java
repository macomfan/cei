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
import cn.ma.cei.generator.environment.Reference;
import cn.ma.cei.generator.langs.golang.tools.GoFile;
import cn.ma.cei.generator.langs.golang.tools.GoStruct;
import cn.ma.cei.model.types.xBoolean;
import cn.ma.cei.model.types.xDecimal;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.model.types.xLong;
import cn.ma.cei.model.types.xString;

/**
 *
 * @author U0151316
 */
public class GoExchangeBuilder extends ExchangeBuilder {

    private GoFile mainFile;
    private GoStruct signatureStruct;

    @Override
    public void startExchange(String exchangeName) {
        Constant.requestMethod().tryPut(RestfulRequest.RequestMethod.GET, "restful.GET");
        Constant.requestMethod().tryPut(RestfulRequest.RequestMethod.POST, "restful.POST");

        Constant.signatureMethod().tryPut(SignatureTool.Constant.ASC, "signature.ASC");
        Constant.signatureMethod().tryPut(SignatureTool.Constant.DSC, "signature.DSC");
        Constant.signatureMethod().tryPut(SignatureTool.Constant.HOST, "signature.HOST");
        Constant.signatureMethod().tryPut(SignatureTool.Constant.METHOD, "signature.METHOD");
        Constant.signatureMethod().tryPut(SignatureTool.Constant.TARGET, "signature.TARGET");
        Constant.signatureMethod().tryPut(SignatureTool.Constant.UPPERCASE, "signature.UPPERCASE");
        Constant.signatureMethod().tryPut(SignatureTool.Constant.LOWERCASE, "signature.LOWERCASE");
        Constant.signatureMethod().tryPut(SignatureTool.Constant.NONE, "signature.NONE");

        Reference.setupBuildinVariableType(xString.inst.getType(), "string", Reference.NO_REF);
        Reference.setupBuildinVariableType(xBoolean.inst.getType(), "bool", Reference.NO_REF);
        Reference.setupBuildinVariableType(xInt.inst.getType(), "int", Reference.NO_REF);
        Reference.setupBuildinVariableType(xLong.inst.getType(), "int64", Reference.NO_REF);
        Reference.setupBuildinVariableType(xDecimal.inst.getType(), "float64", Reference.NO_REF);
        Reference.setupBuildinVariableType(TheArray.getType(), "[]", Reference.NO_REF);
        Reference.setupBuildinVariableType(RestfulRequest.getType(), "RestfulRequest", "impl/restful");
        Reference.setupBuildinVariableType(RestfulResponse.getType(), "RestfulResponse", "impl/restful");
        Reference.setupBuildinVariableType(RestfulConnection.getType(), "RestfulConnection", "impl/restful");
        Reference.setupBuildinVariableType(RestfulOptions.getType(), "RestfulOptions", "impl/restful");
        Reference.setupBuildinVariableType(JsonWrapper.getType(), "JsonWrapper", "impl/json");
        Reference.setupBuildinVariableType(SignatureTool.getType(), "SignatureTool", "impl/signature");

        CEIPath workingFolder = Environment.getWorkingFolder();
        CEIPath exchangeFolder = CEIPath.appendPath(workingFolder, "src", "exchanges");
        exchangeFolder.mkdirs();
        Environment.setExchangeFolder(exchangeFolder);

        mainFile = new GoFile(exchangeName, "exchanges");
        signatureStruct = new GoStruct("Signature");
    }

    @Override
    public RestfulClientBuilder getRestfulClientBuilder() {
        return new GoRestfulClientBuilder(mainFile);
    }

    @Override
    public SignatureBuilder getSignatureBuilder() {
        return new GoSignatureBuilder(signatureStruct);
    }

    @Override
    public ModelBuilder getModelBuilder() {
        return new GoModelBuilder(mainFile);
    }

    @Override
    public void endExchange() {
        if (signatureStruct != null) {
            mainFile.addStruct(signatureStruct);
        }
        mainFile.build(Environment.getExchangeFolder());
    }

}
