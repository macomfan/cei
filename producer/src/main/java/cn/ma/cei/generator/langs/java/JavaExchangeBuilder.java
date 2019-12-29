package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.CEIPath;
import cn.ma.cei.generator.builder.ExchangeBuilder;
import cn.ma.cei.generator.builder.ModelBuilder;
import cn.ma.cei.generator.builder.RestfulClientBuilder;
import cn.ma.cei.generator.builder.RestfulInterfaceBuilder;
import cn.ma.cei.generator.buildin.JsonWrapper;
import cn.ma.cei.generator.buildin.RestfulConnection;
import cn.ma.cei.generator.buildin.RestfulOption;
import cn.ma.cei.generator.buildin.RestfulRequest;
import cn.ma.cei.generator.buildin.RestfulResponse;
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

    private final String fileFolder = "C:\\dev\\cei\\framework\\cei_java";
    private JavaClass mainClass;

    @Override
    public void startExchange(String exchangeName) {
        Environment.setCurrentExchange(exchangeName);
        Environment.setCurrentLanguage(Environment.Language.java);

        Constant.requestMethod().tryPut(RestfulInterfaceBuilder.RequestMethod.GET, "RestfulRequest.Method.GET");
        Constant.requestMethod().tryPut(RestfulInterfaceBuilder.RequestMethod.POST, "RestfulRequest.Method.GET");

        Reference.setupBuildinVariableType(xString.typeName, "String", JavaKeyword.NO_REF);
        Reference.setupBuildinVariableType(xBoolean.typeName, "Boolean", JavaKeyword.NO_REF);
        Reference.setupBuildinVariableType(xInt.typeName, "Integer", JavaKeyword.NO_REF);
        Reference.setupBuildinVariableType(xLong.typeName, "Long", JavaKeyword.NO_REF);
        Reference.setupBuildinVariableType(xDecimal.typeName, "BigDecimal", "java.math.BigDecimal");
        Reference.setupBuildinVariableType("array", "List", "java.util.List");
        Reference.setupBuildinVariableType(TheLinkedList.typeName, "LinkedList", "java.util.LinkedList");
        Reference.setupBuildinVariableType(RestfulRequest.typeName, "RestfulRequest", "cn.ma.cei.impl.RestfulRequest");
        Reference.setupBuildinVariableType(RestfulResponse.typeName, "RestfulResponse", "cn.ma.cei.impl.RestfulResponse");
        Reference.setupBuildinVariableType(RestfulConnection.typeName, "RestfulConnection", "cn.ma.cei.impl.RestfulConnection");
        Reference.setupBuildinVariableType(RestfulOption.typeName, "RestfulOption", "cn.ma.cei.impl.RestfulOption");
        Reference.setupBuildinVariableType(JsonWrapper.typeName, "JsonWrapper", "cn.ma.cei.impl.JsonWrapper");

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
    public void endExchange() {
        mainClass.build(Environment.getExchangeFolder());
    }

}
