package cn.ma.cei.generator.langs.cpp;

import cn.ma.cei.generator.builder.RestfulClientBuilder;
import cn.ma.cei.generator.builder.RestfulInterfaceBuilder;
import cn.ma.cei.generator.langs.cpp.tools.CppClass;

public class CppRestfulClientBuilder extends RestfulClientBuilder {
    private CppClass cppClass;
    private String exchangeName;
    
    public CppRestfulClientBuilder(String exchangeName) {
        this.exchangeName = exchangeName;
    }
    
    @Override
    public void startClient(String clientName) {
        cppClass = new CppClass(exchangeName, clientName);
    }

    @Override
    public RestfulInterfaceBuilder getRestfulInterfaceBuilder() {
        return new CppRestfulInterfaceBuilder(cppClass);
    }

    @Override
    public void endClient() {
        cppClass.build();
    }
}
