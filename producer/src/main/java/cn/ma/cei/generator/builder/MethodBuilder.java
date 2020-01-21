package cn.ma.cei.generator.builder;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.utils.UniquetList;

import java.lang.reflect.Constructor;
import java.util.List;

public abstract class MethodBuilder {

    private int tempVariableIndex = 0;

    public abstract void onAddReference(VariableType variableType);

    private final UniquetList<String, Variable> variableList = new UniquetList<>();

    public Variable createLocalVariable(VariableType type, String name) {
        Variable variable = null;
        if (variableList.containsKey(name)) {
            variable = VariableFactory.createLocalVariable(type, name + Integer.toString(tempVariableIndex++));
        } else {
            variable = VariableFactory.createLocalVariable(type, name);
        }
        registerVariable(variable);
        return variable;
    }

    public Variable createInputVariable(VariableType type, String name) {
        Variable variable = VariableFactory.createInputVariable(type, name);
        registerVariable(variable);
        return variable;
    }

    private void registerVariable(Variable variable) {
        if (variableList.containsKey(variable.getName())) {
            throw new CEIException("Variable re-defined: " + variable.getName());
        }
        variableList.put(variable.getName(), variable);
    }

    public List<Variable> getVariableList() {
        return variableList.values();
    }

    public Variable queryVariable(String name) {
        return variableList.get(name);
    }

    public Variable newInputVariable(VariableType type, String name) {
        String variableName = VariableFactory.isReference(name);
        if (variableName == null) {
            throw new CEIException("[MethodBuilder] Variable name must be {}");
        }
        if (variableList.containsKey(variableName)) {
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
        return variableList.get(variableName);
    }

    public Variable newLocalVariable(VariableType type, String name) {
        String variableName = VariableFactory.isReference(name);
        if (variableName == null) {
            throw new CEIException("[MethodBuilder] Variable name must be {}");
        }
        if (variableList.containsKey(variableName)) {
            throw new CEIException("[MethodBuilder] Variable re-defined: " + variableName);
        }
        Variable variable = VariableFactory.createLocalVariable(type, variableName);
        registerVariable(variable);
        return variable;
    }

    public abstract void startMethod(VariableType returnType, String methodDescriptor, List<Variable> params);

    public abstract void endMethod();
}
