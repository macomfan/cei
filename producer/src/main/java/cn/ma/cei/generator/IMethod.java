package cn.ma.cei.generator;

import java.util.List;

public interface IMethod {
    String getDescriptor();

    VariableType getReturnType();

    List<Variable> getInputVariableList();
}
