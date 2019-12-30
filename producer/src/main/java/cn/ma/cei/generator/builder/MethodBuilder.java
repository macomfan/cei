package cn.ma.cei.generator.builder;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableList;
import cn.ma.cei.generator.environment.VariableType;

public abstract class MethodBuilder {
    public abstract void onAddReference(VariableType variableType);

    private VariableList variableList = new VariableList();

    public void registerVariable(Variable variable) {
        if (variableList.contains(variable.name)) {
            throw new CEIException("Variable re-defined: " + variable.name);
        }
        variableList.registerVariable(variable);
        onAddReference(variable.type);
    }

    public VariableList getVariableList() {
        return variableList;
    }

    public Variable queryVariable(String name) {
        return variableList.queryVariable(name);
    }

//    @FunctionalInterface
//    public interface MethodImplementation {
//        void inMethod();
//    }
    
    public abstract void startMethod(VariableType returnType, String methodDescriptor, VariableList params);
    
    public abstract void endMethod();
}
