package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.utils.RegexHelper;
import cn.ma.cei.utils.UniquetList;

import java.util.LinkedList;
import java.util.List;

public class sMethod {
    private String name;
    private UniquetList<String, Variable> variableList = new UniquetList<>();
    private VariableType returnType;

    public void setReturnType(VariableType returnType) {
        this.returnType = returnType;
    }

    public VariableType getReturnType() {
        return this.returnType;
    }

    public sMethod(String name) {
        this.name = name;
    }

    public String getDescriptor() {
        return GlobalContext.getCurrentDescriptionConverter().getMethodDescriptor(this.name);
    }


    public Variable getVariable(String variableName) {
        return variableList.get(variableName);
    }

    public List<Variable> getInputVariableList() {
        List<Variable> res = new LinkedList<>();
        variableList.values().forEach(item -> {
            if (item.position == Variable.Position.INPUT) {
                res.add(item);
            }
        });
        return res;
    }

    public Variable createInputVariable(VariableType type, String variableName) {
        if (variableList.containsKey(variableName)) {
            throw new CEIException("[Method] ");
        }
        Variable variable = VariableCreator.createInputVariable(type, variableName);
        variableList.put(variableName, variable);
        return variable;
    }

    public Variable createLocalVariable(VariableType type, String variableName) {
        if (variableList.containsKey(variableName)) {
            throw new CEIException("[Method] ");
        }
        Variable variable = VariableCreator.createLocalVariable(type, variableName);
        variableList.put(variableName, variable);
        return variable;
    }

    public Variable createTempVariable(VariableType type, String variableName) {
        if (variableList.containsKey(variableName)) {
            throw new CEIException("[Method] ");
        }
        Variable variable = VariableCreator.createLocalVariable(type, variableName);
        variableList.put(variableName, variable);
        return variable;
    }

    public Variable getVariableAsParam(String name) {
        String variableName = RegexHelper.isReference(name);
        if (variableName == null) {
            return GlobalContext.createStringConstant(name);
        }
        return variableList.get(variableName);
    }

}
