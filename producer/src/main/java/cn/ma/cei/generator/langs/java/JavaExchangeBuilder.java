package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.builder.ExchangeBuilder;
import cn.ma.cei.generator.builder.ModelBuilder;
import cn.ma.cei.generator.builder.RestfulClientBuilder;
import cn.ma.cei.generator.builder.RestfulInterfaceBuilder;
import cn.ma.cei.generator.buildin.JsonWrapper;
import cn.ma.cei.generator.buildin.RestfulConnection;
import cn.ma.cei.generator.buildin.RestfulRequest;
import cn.ma.cei.generator.buildin.RestfulResponse;
import cn.ma.cei.generator.environment.Constant;
import cn.ma.cei.generator.environment.Environment;
import cn.ma.cei.generator.environment.Reference;
import cn.ma.cei.model.types.xBoolean;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.model.types.xString;
import java.io.File;

public class JavaExchangeBuilder extends ExchangeBuilder {

    private final String fileFolder = "C:\\dev\\cei\\framework\\cei_java";

    @Override
    public void startExchange(String exchangeName) {
        Environment.setCurrentExchange(exchangeName);
        Environment.setCurrentLanguage(Environment.Language.java);

        Constant.requestMethod().tryPut(RestfulInterfaceBuilder.RequestMethod.GET, "RestfulRequest.Method.GET");
        Constant.requestMethod().tryPut(RestfulInterfaceBuilder.RequestMethod.POST, "RestfulRequest.Method.GET");

        Reference.setupBuildinVariableType(xString.typeName, "String", JavaKeyword.NO_REF);
        Reference.setupBuildinVariableType(xBoolean.typeName, "Boolean", JavaKeyword.NO_REF);
        Reference.setupBuildinVariableType(xInt.typeName, "Integer", JavaKeyword.NO_REF);
        Reference.setupBuildinVariableType("list", "List", "java.util.List");
        Reference.setupBuildinVariableType("linkedlist", "LinkedList", "java.util.LinkedList");
        Reference.setupBuildinVariableType(RestfulRequest.typeName, "RestfulRequest", "cn.ma.cei.sdk.impl.RestfulRequest");
        Reference.setupBuildinVariableType(RestfulResponse.typeName, "RestfulResponse", "cn.ma.cei.sdk.impl.RestfulResponse");
        Reference.setupBuildinVariableType(RestfulConnection.typeName, "RestfulConnection", "cn.ma.cei.sdk.impl.RestfulConnection");
        Reference.setupBuildinVariableType(JsonWrapper.typeName, "JsonWrapper", "cn.ma.cei.sdk.impl.JsonWrapper");
        
        File workingFolder = Environment.getWorkingFolder();
        File exchangeFolder = new File(workingFolder.getPath() + File.separator + "src/main/java/cn/ma/cei/sdk/exchanges" + File.separator + exchangeName);
        exchangeFolder.mkdirs();
        Environment.setWorkingFolder(exchangeFolder);
        
        File modelFile = new File(exchangeFolder.getPath() + File.separator + "models");
        modelFile.mkdirs();
        File serviceFile = new File(exchangeFolder.getPath() + File.separator + "services");
        serviceFile.mkdirs();
    }

    @Override
    public RestfulClientBuilder getRestfulClientBuilder() {
        return new JavaRestfulClientBuilder();
    }

    @Override
    public ModelBuilder getModelBuilder() {
        return new JavaModelBuilder();
    }

    @Override
    public void endExchange() {

    }

}
