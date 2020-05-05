package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIErrorType;
import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.dataprocessor.TypeConverter;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.RegexHelper;
import cn.ma.cei.utils.UniqueList;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

class sMethod implements IMethod {
    public static final String SELF = "###SELF###";

    private final String name;
    private final UniqueList<String, Variable> variableList = new UniqueList<>();
    private final UniqueList<String, sMethod> nestedMethodList = new UniqueList<>();
    private VariableType returnType;
    private int temporaryId = 0;
    private final VariableType parent;
    private final Variable self;

    public sMethod(VariableType parent, String name) {
        this.parent = parent;
        this.name = name;
        self = createLocalVariable(parent, SELF);
    }

    public sMethod createNestedMethod(String name) {
        sMethod method = new sMethod(parent, name);
        method.temporaryId = temporaryId;
        nestedMethodList.put(name, method);
        variableList.values().forEach(item -> {
            if (!item.getName().equals(SELF)) {
                method.createLocalVariable(item.getType(), item.getName());
            }
        });
        return method;
    }

    @Override
    public VariableType getReturnType() {
        return this.returnType;
    }

    public void setReturnType(VariableType returnType) {
        this.returnType = returnType;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getDescriptor() {
        return GlobalContext.getCurrentDescriptionConverter().getMethodDescriptor(this.name);
    }

    @Override
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
        if (Checker.isEmpty(name)) {
            CEIErrors.showCodeFailure(this.getClass(), "Getting a null variable");
        }
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
     * Query the variable from the current method. the name should only be the variable name format, like {xxxx}.
     * If the name is not like {xxx}, report the error.
     * If the name cannot be found, report the error.
     *
     * @param name the variable name, can be {xxx} or normal string.
     * @return the variable object
     */
    public Variable queryVariable(String name) {
        if (Checker.isEmpty(name)) {
            CEIErrors.showCodeFailure(this.getClass(), "Querying a null variable");
        }
        String variableName = RegexHelper.isReference(name);
        if (Checker.isEmpty(variableName)) {
            CEIErrors.showFailure(CEIErrorType.XML, "%s is not a variable name", name);
            return null;
        }
        return innerQueryVariable(variableName);
    }

    /**
     * Query the variable from the current method. the variableName should only be the variable variableName format, like {xxxx}.
     * If the variableName is not like {xxx}, report the error.
     * If the variableName cannot be found, report the error.
     * After the query successfully, convert to the specified type.
     *
     * @param variableName
     * @param specType
     * @param dataProcessorBuilder
     * @return
     */
    public Variable queryVariable(String variableName, VariableType specType, IDataProcessorBuilder dataProcessorBuilder) {
        Variable res = queryVariable(variableName);
        return TypeConverter.convertType(res, specType, dataProcessorBuilder);
    }

    public Variable tryQueryVariable(String variableName) {
        Variable res = queryVariable(variableName);
        if (res == null) {
            CEIErrors.showFailure(CEIErrorType.XML, "Cannot find variable: %s in method: %s", variableName, name);
        }
        return res;
    }

    public Variable tryQueryVariable(String variableName, VariableType specType, IDataProcessorBuilder dataProcessorBuilder) {
        Variable res = tryQueryVariable(variableName);
        return TypeConverter.convertType(res, specType, dataProcessorBuilder);
    }

    public Variable queryVariableOrConstant(String value, VariableType specType, IDataProcessorBuilder builder) {
        Variable res = queryVariableOrConstant(value, builder);
        return TypeConverter.convertType(res, specType, builder);
    }

    /***
     * Query the variable from the current method.
     * the name should be the variable name format, like {xxx}.
     * If the name is only the variable, like {xxx}, return the variable.
     * If the name is only a string. return the string constant.
     * If the name is mixed, like xxx{xxx}xxx, query the variable and replace {xxx}, then return
     * the statement of String.replace(xxx{xxx}xxx, {xxx}). the stringReplacement in IDataProcessorBuilder will be called.
     *
     * If the name is null or "", return the "" of string constant.
     *
     * @param value the variable name, can be {xxx} or normal string.
     * @return the variable object
     */
    public Variable queryVariableOrConstant(String value, IDataProcessorBuilder builder) {
        String variableName = RegexHelper.isReference(value);
        if (!Checker.isEmpty(variableName)) {
            // The name is {xxx}
            return innerQueryVariable(variableName);
        }
        List<String> variableNames = RegexHelper.findReference(value);
        if (Checker.isNull(variableNames)) {
            // The name is xxx
            if (Checker.isEmpty(value)) {
                return GlobalContext.createStringConstant("");
            }
            return GlobalContext.createStringConstant(value);
        } else {
            // The name is xxx{xxx}xxx
            List<Variable> variables = new LinkedList<>();
            String formatString = value;
            int index = 0;
            for (String item : variableNames) {
                Variable param = GlobalContext.getCurrentMethod().tryGetVariable(item);
                String formatEntity = builder.getStringFormatEntity(index++, param);
                if (Checker.isEmpty(formatEntity)) {
                    CEIErrors.showCodeFailure(builder.getClass(), "getStringFormatEntity is null");
                }
                formatString = formatString.replaceFirst(Pattern.quote("{" + item + "}"), formatEntity);
                variables.add(TypeConverter.convertType(param, xString.inst.getType(), builder));
            }
            variables.add(0, GlobalContext.createStringConstant(formatString));
            Variable[] params = new Variable[variables.size()];
            variables.toArray(params);
            Variable stringReplacement = builder.stringReplacement(params);
            if (stringReplacement == null) {
                CEIErrors.showCodeFailure(this.getClass(), "stringReplacement return null");
            }
            return stringReplacement;
        }
    }

    private Variable innerQueryVariable(String name) {
        String[] variableNames = name.split("\\.");
        if (variableNames.length == 0) {
            // TODO
            // error case
        }
        Variable result = GlobalContext.getCurrentMethod().getVariable(variableNames[0]);
        if (result == null) {
            return null;
        }
        for (int i = 1; i < variableNames.length; i++) {
            result = result.tryGetMember(variableNames[i]);
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
