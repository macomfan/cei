package cn.ma.cei.generator.langs.java;

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
import cn.ma.cei.generator.langs.java.buildin.TheLinkedList;
import cn.ma.cei.generator.langs.java.tools.JavaClass;
import cn.ma.cei.model.types.xBoolean;
import cn.ma.cei.model.types.xDecimal;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.model.types.xLong;
import cn.ma.cei.model.types.xString;

public class JavaExchangeBuilder extends ExchangeBuilder {

    private JavaClass mainClass;
    private JavaClass signatureClass;

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

        Reference.setupBuildinVariableType(xString.inst.getType(), "String", Reference.NO_REF);
        Reference.setupBuildinVariableType(xBoolean.inst.getType(), "Boolean", Reference.NO_REF);
        Reference.setupBuildinVariableType(xInt.inst.getType(), "Integer", Reference.NO_REF);
        Reference.setupBuildinVariableType(xLong.inst.getType(), "Long", Reference.NO_REF);
        Reference.setupBuildinVariableType(xDecimal.inst.getType(), "BigDecimal", "java.math.BigDecimal");
        Reference.setupBuildinVariableType(TheArray.getType(), "List", "java.util.List");
        Reference.setupBuildinVariableType(TheLinkedList.getType(), "LinkedList", "java.util.LinkedList");
        Reference.setupBuildinVariableType(RestfulRequest.getType(), "RestfulRequest", "cn.ma.cei.impl.RestfulRequest");
        Reference.setupBuildinVariableType(RestfulResponse.getType(), "RestfulResponse", "cn.ma.cei.impl.RestfulResponse");
        Reference.setupBuildinVariableType(RestfulConnection.getType(), "RestfulConnection", "cn.ma.cei.impl.RestfulConnection");
        Reference.setupBuildinVariableType(RestfulOptions.getType(), "RestfulOptions", "cn.ma.cei.impl.RestfulOptions");
        Reference.setupBuildinVariableType(JsonWrapper.getType(), "JsonWrapper", "cn.ma.cei.impl.JsonWrapper");
        Reference.setupBuildinVariableType(SignatureTool.getType(), "SignatureTool", "cn.ma.cei.impl.SignatureTool");
        Reference.setupBuildinVariableType(TheStream.getType(), "byte[]", Reference.NO_REF);

        CEIPath workingFolder = Environment.getWorkingFolder();
        CEIPath exchangeFolder = CEIPath.appendPath(workingFolder, "src", "main", "java", "cn", "ma", "cei", "exchanges");
        exchangeFolder.mkdirs();
        Environment.setExchangeFolder(exchangeFolder);

        mainClass = new JavaClass(exchangeName, "cn.ma.cei.exchanges");
    }

    @Override
    public RestfulClientBuilder getRestfulClientBuilder() {
        return new JavaRestfulClientBuilder(mainClass);
    }

    @Override
    public ModelBuilder getModelBuilder() {
        return new JavaModelBuilder(mainClass);
    }

    @Override
    public SignatureBuilder getSignatureBuilder() {
        signatureClass = new JavaClass("Signature");
        return new JavaSignatureBuilder(signatureClass);
    }

    @Override
    public void endExchange() {
        if (signatureClass != null) {
            mainClass.addInnerClass(signatureClass);
        }
        mainClass.build(Environment.getExchangeFolder());
    }
}
