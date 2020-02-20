package cn.ma.cei.generator.builder;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.utils.UniquetList;

import java.util.List;

public abstract class MethodBuilder {

    private int tempVariableIndex = 0;

    public abstract void onAddReference(VariableType variableType);

    public abstract JsonBuilderBuilder createJsonBuilderBuilder();

    public abstract StringBuilderBuilder createStringBuilderBuilder();

    private final UniquetList<String, Variable> variableList = new UniquetList<>();

//    public Variable createLocalVariable(VariableType type, String name) {
//        if (variableList.containsKey(name)) {
//            throw new CEIException("Duplicate variable " + name);
//        }
//        Variable variable = VariableCreator.createLocalVariable(type, name);
//        registerVariable(variable);
//        return variable;
//    }
//
//    public Variable createTempVariable(VariableType type, String name) {
//        String newName = name;
//        if (variableList.containsKey(newName)) {
//            newName += Integer.toString(tempVariableIndex++);
//        }
//        return createLocalVariable(type, newName);
//    }
//
//    public Variable createInputVariable(VariableType type, String name) {
//        Variable variable = VariableCreator.createInputVariable(type, name);
//        registerVariable(variable);
//        return variable;
//    }
//
//    private void registerVariable(Variable variable) {
//        if (variableList.containsKey(variable.getName())) {
//            throw new CEIException("Variable re-defined: " + variable.getName());
//        }
//        variableList.put(variable.getName(), variable);
//    }
//
//    public List<Variable> getVariableList() {
//        return variableList.values();
//    }
//
//    public Variable queryVariable(String name) {
//        return variableList.get(name);
//    }
//
//    public Variable newInputVariable(VariableType type, String name) {
//        String variableName = VariableFactory.isReference(name);
//        if (variableName == null) {
//            throw new CEIException("[MethodBuilder] Variable name must be {}");
//        }
//        if (variableList.containsKey(variableName)) {
//            throw new CEIException("[MethodBuilder] Variable re-defined: " + variableName);
//        }
//        Variable variable = VariableCreator.createInputVariable(type, variableName);
//        registerVariable(variable);
//        return variable;
//    }
//
//    public Variable queryVariableAsParam(String name) {
//        String variableName = VariableFactory.isReference(name);
//        if (variableName == null) {
//            return VariableFactory.createHardcodeStringVariable(name);
//        }
//        return variableList.get(variableName);
//    }
//
//    public Variable newLocalVariable(VariableType type, String name) {
//        String variableName = VariableFactory.isReference(name);
//        if (variableName == null) {
//            throw new CEIException("[MethodBuilder] Variable name must be {}");
//        }
//        if (variableList.containsKey(variableName)) {
//            throw new CEIException("[MethodBuilder] Variable re-defined: " + variableName);
//        }
//        Variable variable = VariableCreator.createLocalVariable(type, variableName);
//        registerVariable(variable);
//        return variable;
//    }

    public abstract void startMethod(VariableType returnType, String methodDescriptor, List<Variable> params);

    public abstract void endMethod();
}
