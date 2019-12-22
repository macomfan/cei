package cn.ma.cei.generator.langs.cpp;

import cn.ma.cei.generator.builder.ExchangeBuilder;
import cn.ma.cei.generator.builder.ModelBuilder;
import cn.ma.cei.generator.builder.RestfulClientBuilder;
import cn.ma.cei.generator.builder.RestfulInterfaceBuilder;
import cn.ma.cei.generator.buildin.RestfulConnection;
import cn.ma.cei.generator.buildin.RestfulRequest;
import cn.ma.cei.generator.buildin.RestfulResponse;
import cn.ma.cei.generator.environment.Constant;
import cn.ma.cei.generator.environment.Environment;
import cn.ma.cei.generator.environment.Reference;
import cn.ma.cei.generator.langs.java.JavaKeyword;
import cn.ma.cei.model.types.xBoolean;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.model.types.xString;

public class CppExchangeBuilder extends ExchangeBuilder {
    @Override
    public void startExchange(String exchangeName) {
        Environment.setCurrentExchange(exchangeName);
        Environment.setCurrentLanguage(Environment.Language.cpp);

        Constant.requestMethod().tryPut(RestfulInterfaceBuilder.RequestMethod.GET, "RestfulRequest.Method.GET");
        Constant.requestMethod().tryPut(RestfulInterfaceBuilder.RequestMethod.POST, "RestfulRequest.Method.GET");

        Reference.setupBuildinVariableType(xString.typeName, "CEIString", JavaKeyword.NO_REF);
        Reference.setupBuildinVariableType(xBoolean.typeName, "CEIBool", JavaKeyword.NO_REF);
        Reference.setupBuildinVariableType(xInt.typeName, "CEIInt", JavaKeyword.NO_REF);
        Reference.setupBuildinVariableType(RestfulRequest.typeName, "RestfulRequest", "impl/RestfulRequest.h");
        Reference.setupBuildinVariableType(RestfulResponse.typeName, "RestfulResponse", "impl/RestfulResponse.h");
        Reference.setupBuildinVariableType(RestfulConnection.typeName, "RestfulConnection", "impl/RestfulConnection.h");
    }

    @Override
    public RestfulClientBuilder getRestfulClientBuilder() {
        return new CppRestfulClientBuilder();
    }

    @Override
    public ModelBuilder getModelBuilder() {
        return new CppModelBuilder();
    }

    @Override
    public void endExchange() {

    }
}
