package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;

import java.util.List;

public interface IMethodBuilder {
    void onAddReference(VariableType variableType);

    void startMethod(VariableType returnType, String methodDescriptor, List<Variable> params);

    IDataProcessorBuilder createDataProcessorBuilder();

    void endMethod(Variable returnVariable);
}
