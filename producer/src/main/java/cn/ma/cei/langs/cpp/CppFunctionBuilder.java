package cn.ma.cei.langs.cpp;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IMethodBuilder;
import cn.ma.cei.langs.cpp.tools.CppClass;
import cn.ma.cei.langs.cpp.tools.CppMethod;

import java.util.List;

public class CppFunctionBuilder implements IMethodBuilder {

    private CppClass parent;
    private CppMethod method;

    public CppFunctionBuilder(CppClass functionClass) {
        this.parent = functionClass;
    }

    @Override
    public void onAddReference(VariableType variableType) {

    }

    @Override
    public void startMethod(VariableType returnType, String methodDescriptor, List<Variable> params) {
        method = new CppMethod(parent);
    }

    @Override
    public IDataProcessorBuilder createDataProcessorBuilder() {
        return new CppDataProcessorBuilder(method);
    }

    @Override
    public void endMethod(Variable returnVariable) {

    }
}
