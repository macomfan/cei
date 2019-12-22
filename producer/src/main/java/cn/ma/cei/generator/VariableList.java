package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.utils.IndexedList;

import java.util.List;

public class VariableList {
    private final IndexedList<String, Variable> variableList = new IndexedList<>();

    public VariableList() {

    }

    public void registerVariable(Variable variable) {
        if (variableList.containsKey(variable.name)) {
            throw new CEIException("[VariableList] Variable redefined: " + variable.name);
        }
        variableList.put(variable.name, variable);
    }

    public boolean contains(String name) {
        return variableList.containsKey(name);
    }

    public List<Variable> getVariableList() {
        return variableList.values();
    }

    public Variable queryVariable(String name) {
        if (contains(name)) {
            return variableList.get(name);
        } else {
            return null;
        }
    }
}
