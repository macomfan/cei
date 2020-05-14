package cn.ma.cei.langs.cpp;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IWebSocketNestedBuilder;
import cn.ma.cei.langs.cpp.tools.CppMethod;

public class CppWebSocketNestedBuilder implements IWebSocketNestedBuilder {

    public CppMethod method;

    public CppWebSocketNestedBuilder(CppMethod method) {
        this.method = method;
    }

    @Override
    public void onAddReference(VariableType variableType) {

    }

    @Override
    public IDataProcessorBuilder createDataProcessorBuilder() {
        return new CppDataProcessorBuilder(method);
    }

    @Override
    public void endMethod(Variable returnVariable) {

    }
}
