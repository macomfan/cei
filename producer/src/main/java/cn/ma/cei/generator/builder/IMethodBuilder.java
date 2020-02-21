package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;

import java.util.List;

public interface IMethodBuilder {

    void onAddReference(VariableType variableType);

    IJsonBuilderBuilder createJsonBuilderBuilder();

    IStringBuilderBuilder createStringBuilderBuilder();

    IJsonParserBuilder createJsonParserBuilder();

    void startMethod(VariableType returnType, String methodDescriptor, List<Variable> params);

    void endMethod();
}
