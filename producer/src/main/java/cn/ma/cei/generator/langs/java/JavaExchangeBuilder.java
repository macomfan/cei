package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.*;
import cn.ma.cei.generator.builder.IExchangeBuilder;
import cn.ma.cei.generator.builder.IModelBuilder;
import cn.ma.cei.generator.builder.IRestfulClientBuilder;
import cn.ma.cei.generator.builder.ISignatureBuilder;
import cn.ma.cei.generator.buildin.*;
import cn.ma.cei.generator.langs.java.buildin.TheLinkedList;
import cn.ma.cei.generator.langs.java.tools.JavaClass;
import cn.ma.cei.model.types.xBoolean;
import cn.ma.cei.model.types.xDecimal;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.model.types.xString;

public class JavaExchangeBuilder implements IExchangeBuilder {

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

        BuilderContext.setupBuildInVariableType(xString.typeName, "String", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(xBoolean.typeName, "Boolean", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(xInt.typeName, "Long", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(xDecimal.typeName, "BigDecimal", "java.math.BigDecimal");
        BuilderContext.setupBuildInVariableType(TheArray.typeName, "List", "java.util.List");
        BuilderContext.setupBuildInVariableType(TheLinkedList.typeName, "LinkedList", "java.util.LinkedList");
        BuilderContext.setupBuildInVariableType(RestfulRequest.typeName, "RestfulRequest", "cn.ma.cei.impl.RestfulRequest");
        BuilderContext.setupBuildInVariableType(RestfulResponse.typeName, "RestfulResponse", "cn.ma.cei.impl.RestfulResponse");
        BuilderContext.setupBuildInVariableType(RestfulConnection.typeName, "RestfulConnection", "cn.ma.cei.impl.RestfulConnection");
        BuilderContext.setupBuildInVariableType(RestfulOptions.typeName, "RestfulOptions", "cn.ma.cei.impl.RestfulOptions");
        BuilderContext.setupBuildInVariableType(JsonWrapper.typeName, "JsonWrapper", "cn.ma.cei.impl.JsonWrapper");
        BuilderContext.setupBuildInVariableType(SignatureTool.typeName, "SignatureTool", "cn.ma.cei.impl.SignatureTool");
        BuilderContext.setupBuildInVariableType(TheStream.typeName, "byte[]", BuilderContext.NO_REF);
        BuilderContext.setupBuildInVariableType(JsonChecker.typeName, "JsonChecker", "cn.ma.cei.impl.JsonChecker");

        CEIPath workingFolder = BuilderContext.getWorkingFolder();
        CEIPath exchangeFolder = CEIPath.appendPath(workingFolder, "src", "main", "java", "cn", "ma", "cei", "exchanges");
        exchangeFolder.mkdirs();
        BuilderContext.setExchangeFolder(exchangeFolder);

        mainClass = new JavaClass(exchangeName, "cn.ma.cei.exchanges");
        signatureClass = new JavaClass("Signature");
    }

    @Override
    public IRestfulClientBuilder getRestfulClientBuilder(VariableType clientType) {
        return new JavaRestfulClientBuilder(clientType, mainClass);
    }

    @Override
    public IModelBuilder getModelBuilder() {
        return new JavaModelBuilder(mainClass);
    }

    @Override
    public ISignatureBuilder getSignatureBuilder() {
        return new JavaSignatureBuilder(signatureClass);
    }

    @Override
    public void endExchange() {
        if (signatureClass != null) {
            mainClass.addInnerClass(signatureClass);
        }
        mainClass.build(BuilderContext.getExchangeFolder());
    }
}
