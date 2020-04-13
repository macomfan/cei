package cn.ma.cei.langs.cpp;

import cn.ma.cei.generator.builder.*;

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
    public IRestfulClientBuilder createRestfulClientBuilder() {
        return new CppRestfulClientBuilder(exchangeName);
    }

    @Override
    public IWebSocketClientBuilder createWebSocketClientBuilder() {
        return null;
    }

    @Override
    public IModelBuilder createModelBuilder() {
        return new CppModelBuilder(exchangeName);
    }

    @Override
    public void endExchange() {

    }

    @Override
    public IAuthenticationBuilder createAuthenticationBuilder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
