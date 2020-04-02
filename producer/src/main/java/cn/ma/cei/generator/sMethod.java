package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIErrorType;
import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.utils.RegexHelper;
import cn.ma.cei.utils.UniqueList;

import java.util.LinkedList;
import java.util.List;

public class sMethod {
    public static final String SELF = "###SELF###";

    private String name;
    private UniqueList<String, Variable> variableList = new UniqueList<>();
    private UniqueList<String, sMethod> nestedMethodList = new UniqueList<>();
    private VariableType returnType;
    private int temporaryId = 0;
    private VariableType parent;
    private Variable self;

    public sMethod(VariableType parent, String name) {
        this.parent = parent;
        this.name = name;
        self = createLocalVariable(parent, SELF);
    }

    public sMethod createNestedMethod(String name) {
        sMethod method = new sMethod(parent, name);
        method.temporaryId = temporaryId;
        nestedMethodList.put(name, method);
        return method;
    }

    public void setReturnType(VariableType returnType) {
        this.returnType = returnType;
    }

    public VariableType getReturnType() {
        return this.returnType;
    }

    public String getName() {
        return name;
    }

    public String getDescriptor() {
        return GlobalContext.getCurrentDescriptionConverter().getMethodDescriptor(this.name);
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
        Variable variable = VariableCreator.createInputVariable(type, variableName);
        return attachVariable(variable);
    }

    public Variable createLocalVariable(VariableType type, String variableName) {
        Variable variable = VariableCreator.createLocalVariable(type, variableName);
        return attachVariable(variable);
    }

    public Variable createUserVariable(VariableType type, String variableName) {
        Variable variable = VariableCreator.createUserVariable(type, variableName);
        return attachVariable(variable);
    }

    public Variable createTempVariable(VariableType type, String variableName) {
        Variable variable = VariableCreator.createLocalVariable(type, temporaryVariableName(variableName));
        return attachVariable(variable);
    }

    private Variable attachVariable(Variable variable) {
        if (variableList.containsKey(variable.getName())) {
            CEIErrors.showFailure(CEIErrorType.XML, "Method: %s has the duplicate variable: %s", name, variable.getName());
        }
        variableList.put(variable.getName(), variable);
        return variable;
    }

    /***
     * Get the variable including input variable and local variable by variable name.
     *
     * @param variableName the variable name
     * @return the variable object
     */
    public Variable getVariable(String variableName) {
        if (variableList.containsKey(variableName)) {
            return variableList.get(variableName);
        } else {
            return self.queryMember(variableName);
        }
    }

    /***
     * Get the variable including input variable and local variable by variable name.
     * When cannot find the variable, this function will throw the exception.
     *
     * @param variableName the variable name
     * @return the variable object
     */
    public Variable tryGetVariable(String variableName) {
        Variable result = getVariable(variableName);
        if (result == null) {
            CEIErrors.showFailure(CEIErrorType.XML, "Cannot find variable: %s in method: %s", variableName, name);
        }
        return result;
    }


    private String temporaryVariableName(String variableName) {
        String name = variableName;
        while (variableList.containsKey(name)) {
            name += temporaryId++;
        }
        return name;
    }

}
