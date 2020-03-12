package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;

import java.util.List;

public interface IWebSocketImplementationBuilder extends IMethodBuilder {

    default void startMethod(VariableType returnType, String methodDescriptor, List<Variable> params) {}

    default void endMethod() {}

    void onAddReference(VariableType variableType);

    void startTrigger();

    void endTrigger();

    void send(Variable connection, Variable send);
}
