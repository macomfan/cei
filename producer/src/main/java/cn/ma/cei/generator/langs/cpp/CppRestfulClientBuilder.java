package cn.ma.cei.generator.langs.cpp;

import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.builder.IRestfulClientBuilder;
import cn.ma.cei.generator.builder.IRestfulInterfaceBuilder;
import cn.ma.cei.generator.langs.cpp.tools.CppClass;
import cn.ma.cei.generator.sMethod;

public class CppRestfulClientBuilder implements IRestfulClientBuilder {
    private CppClass cppClass;
    private String exchangeName;
    
    public CppRestfulClientBuilder(String exchangeName) {
        this.exchangeName = exchangeName;
    }
    
    @Override
    public void startClient(String clientDescriptor, RestfulOptions options) {
        cppClass = new CppClass(exchangeName, clientDescriptor);
    }

    @Override
    public IRestfulInterfaceBuilder createRestfulInterfaceBuilder(sMethod method) {
        return new CppRestfulInterfaceBuilder(cppClass);
    }

    @Override
    public void endClient() {
        cppClass.build();
    }
}
