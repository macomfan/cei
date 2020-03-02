package cn.ma.cei.generator.langs.cpp;

import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IExchangeBuilder;
import cn.ma.cei.generator.builder.IModelBuilder;
import cn.ma.cei.generator.builder.IRestfulClientBuilder;
import cn.ma.cei.generator.builder.ISignatureBuilder;

public class CppExchangeBuilder implements IExchangeBuilder {
    
    private String exchangeName;
    
    @Override
    public void startExchange(String exchangeName) {
        this.exchangeName = exchangeName;

        //Environment.setCurrentLanguage(Environment.Language.cpp);

//        Constant.requestMethod().tryPut(RestfulInterfaceBuilder.RequestMethod.GET, "RestfulRequest.Method.GET");
//        Constant.requestMethod().tryPut(RestfulInterfaceBuilder.RequestMethod.POST, "RestfulRequest.Method.POST");

//        Reference.setupBuildinVariableType(xString.typeName, "CEIString", "\"impl/CEIString.h\"");
//        Reference.setupBuildinVariableType(xBoolean.typeName, "CEIBool", "\"impl/CEIBool.h\"");
//        Reference.setupBuildinVariableType(xInt.typeName, "CEIInt", "\"impl/CEIInt.h\"");
//        Reference.setupBuildinVariableType(RestfulRequest.typeName, "RestfulRequest", "\"impl/RestfulRequest.h\"");
//        Reference.setupBuildinVariableType(RestfulResponse.typeName, "RestfulResponse", "\"impl/RestfulResponse.h\"");
//        Reference.setupBuildinVariableType(RestfulConnection.typeName, "RestfulConnection", "\"impl/RestfulConnection.h\"");
//        Reference.setupBuildinVariableType("array", "std::vevtor", "<vector>");
    }

    @Override
    public IRestfulClientBuilder getRestfulClientBuilder(VariableType clientType) {
        return new CppRestfulClientBuilder(exchangeName);
    }

    @Override
    public IModelBuilder getModelBuilder() {
        return new CppModelBuilder(exchangeName);
    }

    @Override
    public void endExchange() {

    }

    @Override
    public ISignatureBuilder getSignatureBuilder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
