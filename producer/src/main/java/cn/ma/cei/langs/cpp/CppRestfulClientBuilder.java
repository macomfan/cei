package cn.ma.cei.langs.cpp;

import cn.ma.cei.generator.IMethod;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.builder.IRestfulClientBuilder;
import cn.ma.cei.generator.builder.IRestfulInterfaceBuilder;
import cn.ma.cei.langs.cpp.tools.CppClass;

public class CppRestfulClientBuilder implements IRestfulClientBuilder {
    private CppClass cppClass;
    private final String exchangeName;
    
    public CppRestfulClientBuilder(String exchangeName) {
        this.exchangeName = exchangeName;
    }
    
    @Override
    public void startClient(VariableType clientType, RestfulOptions options) {
        cppClass = new CppClass(exchangeName, clientType.getDescriptor());
    }

    @Override
    public IRestfulInterfaceBuilder createRestfulInterfaceBuilder(IMethod method) {
        return new CppRestfulInterfaceBuilder(cppClass);
    }

    @Override
    public void endClient() {
        cppClass.build();
    }
}
