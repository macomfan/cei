package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;

import java.util.List;

public interface IWebSocketNestedBuilder extends IMethodBuilder {

    default void startMethod(VariableType returnType, String methodDescriptor, List<Variable> params) {}

    void endMethod(Variable returnVariable);

    default void endMethod() {}
}
