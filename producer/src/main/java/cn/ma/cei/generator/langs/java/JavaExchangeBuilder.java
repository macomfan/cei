package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.CEIPath;
import cn.ma.cei.generator.builder.ExchangeBuilder;
import cn.ma.cei.generator.builder.ModelBuilder;
import cn.ma.cei.generator.builder.RestfulClientBuilder;
import cn.ma.cei.generator.builder.SignatureBuilder;
import cn.ma.cei.generator.buildin.JsonWrapper;
import cn.ma.cei.generator.buildin.RestfulConnection;
import cn.ma.cei.generator.buildin.RestfulRequest;
import cn.ma.cei.generator.buildin.RestfulResponse;
import cn.ma.cei.generator.buildin.SignatureTool;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.buildin.TheArray;
import cn.ma.cei.generator.buildin.TheStream;
import cn.ma.cei.generator.environment.Constant;
import cn.ma.cei.generator.environment.Environment;
import cn.ma.cei.generator.environment.VariableFactory;
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

        VariableFactory.setupBuildinVariableType(xString.typeName, "String", VariableFactory.NO_REF);
        VariableFactory.setupBuildinVariableType(xBoolean.typeName, "Boolean", VariableFactory.NO_REF);
        VariableFactory.setupBuildinVariableType(xInt.typeName, "Integer", VariableFactory.NO_REF);
        VariableFactory.setupBuildinVariableType(xLong.typeName, "Long", VariableFactory.NO_REF);
        VariableFactory.setupBuildinVariableType(xDecimal.typeName, "BigDecimal", "java.math.BigDecimal");
        VariableFactory.setupBuildinVariableType(TheArray.typeName, "List", "java.util.List");
        VariableFactory.setupBuildinVariableType(TheLinkedList.typeName, "LinkedList", "java.util.LinkedList");
        VariableFactory.setupBuildinVariableType(RestfulRequest.typeName, "RestfulRequest", "cn.ma.cei.impl.RestfulRequest");
        VariableFactory.setupBuildinVariableType(RestfulResponse.typeName, "RestfulResponse", "cn.ma.cei.impl.RestfulResponse");
        VariableFactory.setupBuildinVariableType(RestfulConnection.typeName, "RestfulConnection", "cn.ma.cei.impl.RestfulConnection");
        VariableFactory.setupBuildinVariableType(RestfulOptions.typeName, "RestfulOptions", "cn.ma.cei.impl.RestfulOptions");
        VariableFactory.setupBuildinVariableType(JsonWrapper.typeName, "JsonWrapper", "cn.ma.cei.impl.JsonWrapper");
        VariableFactory.setupBuildinVariableType(SignatureTool.typeName, "SignatureTool", "cn.ma.cei.impl.SignatureTool");
        VariableFactory.setupBuildinVariableType(TheStream.typeName, "byte[]", VariableFactory.NO_REF);

        CEIPath workingFolder = Environment.getWorkingFolder();
        CEIPath exchangeFolder = CEIPath.appendPath(workingFolder, "src", "main", "java", "cn", "ma", "cei", "exchanges");
        exchangeFolder.mkdirs();
        Environment.setExchangeFolder(exchangeFolder);

        mainClass = new JavaClass(exchangeName, "cn.ma.cei.exchanges");
        signatureClass = new JavaClass("Signature");
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
