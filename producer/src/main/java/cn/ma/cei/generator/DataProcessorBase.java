package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIErrorType;
import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.dataprocessor.TypeConverter;
import cn.ma.cei.model.base.xDataProcessorItem;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.RegexHelper;

import java.util.LinkedList;
import java.util.List;

public abstract class DataProcessorBase<T extends xDataProcessorItem> {
    private Variable defaultInput = null;
    private IDataProcessorBuilder builder = null;

    public Variable callBuild(xDataProcessorItem item, Variable defaultInput, IDataProcessorBuilder builder) {
        if (item != null) {
            this.defaultInput = defaultInput;
            this.builder = builder;
            Variable result = build(convertTo(item), builder);
            this.defaultInput = null;
            this.builder = null;
            return result;
        }
        return null;
    }

    public VariableType callReturnType(xDataProcessorItem item) {
        if (item != null) {
            return returnType(convertTo(item));
        }
        return null;
    }

    public String callResultVariableName(xDataProcessorItem item) {
        if (item != null) {
            return resultVariableName(convertTo(item));
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private T convertTo(xDataProcessorItem item) {
        return (T) item;
    }

    public Variable createTempVariable(VariableType type, String name) {
        return GlobalContext.getCurrentMethod().createTempVariable(type, name);
    }

    public Variable createLocalVariable(VariableType type, String name) {
        String variableName = RegexHelper.isReference(name);
        if (variableName == null) {
            CEIErrors.showFailure(CEIErrorType.XML, "%s should be {xxx}", name);
        }
        return GlobalContext.getCurrentMethod().createLocalVariable(type, variableName);
    }

    public Variable getDefaultInput() {
        return defaultInput;
    }

    public Variable queryVariableOrConstant(String name, VariableType specType) {
        return TypeConverter.convertType(queryVariableOrConstant(name), specType, builder);
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
     * @param name the variable name, can be {xxx} or normal string.
     * @return the variable object
     */
    public Variable queryVariableOrConstant(String name) {
        String variableName = RegexHelper.isReference(name);
        if (variableName != null) {
            // The name is {xxx}
            return innerQueryVariable(variableName);
        }
        List<String> variableNames = RegexHelper.findReference(name);
        if (Checker.isNull(variableNames)) {
            // The name is xxx
            if (Checker.isEmpty(name)) {
                return GlobalContext.createStringConstant("");
            }
            return GlobalContext.createStringConstant(name);
        } else {
            // The name is xxx{xxx}xxx
            List<Variable> variables = new LinkedList<>();
            variables.add(GlobalContext.createStringConstant(name));
            variableNames.forEach(item -> {
                Variable param = GlobalContext.getCurrentMethod().getVariable(item);
                if (param == null) {
                    throw new CEIException("Cannot find variable in target");
                }
                variables.add(param);
            });
            Variable[] params = new Variable[variables.size()];
            variables.toArray(params);
            return builder.stringReplacement(params);
        }

    }

    /***
     * Query the variable from the current method. the name should only be the variable name format, like {xxxx}.
     * If the name is not like {xxx}, report the error.
     *
     * @param name the variable name, can be {xxx} or normal string.
     * @return the variable object
     */
    public Variable queryVariable(String name) {
        String variableName = RegexHelper.isReference(name);
        if (variableName == null) {
            CEIErrors.showFailure(CEIErrorType.XML, "No a variable name");
        }
        return GlobalContext.getCurrentMethod().tryGetVariable(variableName);
//        if (variable == null) {
//            CEIErrors.showFailure(CEIErrorType.XML, "C");
////            Variable options = GlobalContext.getCurrentMethod().getVariable("options");
////            if (options == null) {
////                throw new CEIException("[BuildSignature] Cannot query option");
////            }
////            String variableName = RegexHelper.isReference(name);
////            variable = options.getMember(variableName);
////            if (variable == null) {
////                throw new CEIException("[BuildSignature] cannot query variable: " + name);
////            }
//        }
//        return variable;
    }

    private Variable innerQueryVariable(String name) {
        String[] variableNames = name.split("\\.");
        if (variableNames.length == 0) {
            // TODO
        }
        Variable result = GlobalContext.getCurrentMethod().tryGetVariable(variableNames[0]);
        for (int i = 1; i < variableNames.length; i++) {
            result = result.getType().tryGetMember(variableNames[i]);
        }
        return result;
    }

    public abstract Variable build(T item, IDataProcessorBuilder builder);

    /**
     * Return the VariableType of the result of this processor.
     * e.g.
     * return xString.inst.getType();
     * <p>
     * Should NOT return null unless there is an error in the processor.
     * If this processor will not return any data, return VariableType.VOID
     *
     * @param item the VariableType of the result, null when error happen
     * @return
     */
    public abstract VariableType returnType(T item);


    public abstract String resultVariableName(T item);
}
