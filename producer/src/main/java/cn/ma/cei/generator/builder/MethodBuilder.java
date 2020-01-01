package cn.ma.cei.generator.builder;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableFactory;
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
        //onAddReference(variable.type);
    }

    public VariableList getVariableList() {
        return variableList;
    }

    public Variable queryVariable(String name) {
        return variableList.queryVariable(name);
    }

    public Variable newInputVariable(VariableType type, String name) {
        String variableName = VariableFactory.isReference(name);
        if (variableName == null) {
            throw new CEIException("[MethodBuilder] Variable name must be {}");
        }
        if (variableList.contains(variableName)) {
            throw new CEIException("[MethodBuilder] Variable re-defined: " + variableName);
        }
        Variable variable = VariableFactory.createInputVariable(type, variableName);
        registerVariable(variable);
        return variable;
    }
    
    public Variable queryVariableAsParam(String name) {
        String variableName = VariableFactory.isReference(name);
        if (variableName == null) {
            return VariableFactory.createHardcodeStringVariable(name);
        }
        return variableList.queryVariable(variableName);
    }

    public Variable newLoaclVariable(VariableType type, String name) {
        String variableName = VariableFactory.isReference(name);
        if (variableName == null) {
            throw new CEIException("[MethodBuilder] Variable name must be {}");
        }
        if (variableList.contains(variableName)) {
            throw new CEIException("[MethodBuilder] Variable re-defined: " + variableName);
        }
        Variable variable = VariableFactory.createLocalVariable(type, variableName);
        registerVariable(variable);
        return variable;
    }

    public abstract void startMethod(VariableType returnType, String methodDescriptor, VariableList params);

    public abstract void endMethod();
}
