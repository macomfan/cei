package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIErrorType;
import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.exception.CEIException;
import cn.ma.cei.utils.RegexHelper;
import cn.ma.cei.utils.UniqueList;

import java.util.LinkedList;
import java.util.List;

public class sMethod {
    private String name;
    private UniqueList<String, Variable> variableList = new UniqueList<>();
    private UniqueList<String, sMethod> nestedMethodList = new UniqueList<>();
    private VariableType returnType;
    private int temporaryId = 0;
    private VariableType parent = null;
    private Variable self = null;

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

    public sMethod(VariableType parent, String name) {
        this.parent = parent;
        this.name = name;
        self = createLocalVariable(parent, GlobalContext.getCurrentDescriptionConverter().getSelfDescriptor());
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
        Variable variable = VariableCreator.createLocalVariable(type, temporaryVariableName(variableName));
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
            return self.getMember(variableName);
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

    /***
     * Get the variable including input variable and local variable by user defined variable name.
     * The user defined variable means the variable name defined in XML file.
     * e.g.
     * If the XML define "{timestamp}", the getVariableAsParam will query the variable named "timestamp".
     * If the name is "{option.APIKey}", the getVariableAsParam will query "option" firstly, then query the member
     * "APIKey" from "option".
     *
     * @param name the user defined variable name
     * @return the variable object, if no specified variable or the name is not "{xxx}", return null
     */
    public Variable getVariableAsParam(String name) {
        String variableName = RegexHelper.isReference(name);
        if (variableName == null) {
            return null;
        }
        if (variableName.indexOf('.') != -1) {
            String[] variables = variableName.split("\\.");
            if (variables.length < 2) {
                CEIErrors.showFailure(CEIErrorType.XML, "Variable name is invalid, expected is {xxx.xxx}, current is %s", name);
            }
            Variable base = getVariable(variables[0]);
            if (base == null) {
                CEIErrors.showFailure(CEIErrorType.XML, "Cannot query %s", name);
            }
        }
        return variableList.get(variableName);
    }

    private String temporaryVariableName(String variableName) {
        String name = variableName;
        while (variableList.containsKey(name)) {
            name += temporaryId++;
        }
        return name;
    }

}
