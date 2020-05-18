package cn.ma.cei.langs.cpp;

import cn.ma.cei.generator.IMethod;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IRestfulClientBuilder;
import cn.ma.cei.generator.builder.IRestfulInterfaceBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.langs.cpp.tools.CppClass;
import cn.ma.cei.langs.cpp.tools.CppExchangeFile;

public class CppRestfulClientBuilder implements IRestfulClientBuilder {
    private CppClass cppClass;
    private final CppExchangeFile file;
    
    public CppRestfulClientBuilder(CppExchangeFile exchange) {
        this.file = exchange;
    }
    
    @Override
    public void startClient(VariableType client, RestfulOptions option, Variable optionVariable) {
        cppClass = new CppClass(client.getDescriptor());
    }

    @Override
    public IRestfulInterfaceBuilder createRestfulInterfaceBuilder(IMethod method) {
        return new CppRestfulInterfaceBuilder(cppClass);
    }

    @Override
    public void endClient() {
        file.addClass(cppClass);
    }
}
