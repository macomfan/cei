package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIErrorType;
import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.dataprocessor.TypeConverter;
import cn.ma.cei.model.base.xDataProcessorItem;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.RegexHelper;

import java.util.List;

public abstract class DataProcessorBase<T extends xDataProcessorItem> {
    private Variable defaultInput = null;
    private IDataProcessorBuilder builder = null;

    public Variable callBuild(xDataProcessorItem item, Variable defaultInput, IDataProcessorBuilder builder) {
        if (item != null) {
            item.startBuilding();
            this.defaultInput = defaultInput;
            this.builder = builder;
            Variable result = build(convertTo(item), builder);
            this.defaultInput = null;
            this.builder = null;
            item.endBuilding();
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

    public Variable createUserVariable(VariableType type, String name) {
        String variableName = RegexHelper.isReference(name);
        if (variableName != null) {
            CEIErrors.showFailure(CEIErrorType.XML, "Variable name %s is not valid, should be {xxx}", name);
        }
        return GlobalContext.getCurrentMethod().createUserVariable(type, name);
    }

    public Variable getDefaultInput() {
        return defaultInput;
    }

    public Variable queryInputVariable(String inputName, String defaultInputName, VariableType type) {
        Variable result = null;
        if (!Checker.isEmpty(inputName)) {
            return tryQueryVariable(inputName, type);
        }
        if (!Checker.isEmpty(defaultInputName)) {
            result = queryVariable(defaultInputName);
            if (result != null) {
                return result;
            }
        }
        if (getDefaultInput() != null) {
            return TypeConverter.convertType(getDefaultInput(), type, builder);
        }
        List<Variable> variableList = GlobalContext.getCurrentMethod().getInputVariableList();
        int found = -1;// Not found: -1, Found: i, Multi Fount： -2
        int i = 0;
        for (Variable variable : variableList) {
            if (variable.getType() == type) {
                if (found == -1) {
                    found = i;
                } else {
                    found = -2;
                }
            }
            i++;
        }
        if (found == -1) {
            CEIErrors.showXMLFailure("Cannot found default input %s, and no any input with type %s", defaultInputName, type.getDescriptor());
        } else if (found == -2) {
            CEIErrors.showXMLFailure("Cannot decide input by type %s, multi-inputs are found", type.getDescriptor());
        } else {
            result = variableList.get(found);
        }

        return result;
    }

    public Variable getOneInputVariableByType(VariableType type) {

        return null;
    }

    /***
     * See {@link DataProcessorBase#queryVariableOrConstant(String)}.
     * Not only query the variable, and also convert to the specified type.
     *
     * @param name
     * @param specType
     * @return
     */
    public Variable queryVariableOrConstant(String name, VariableType specType) {
        return TypeConverter.convertType(queryVariableOrConstant(name), specType, builder);
    }

    /***
     * Query the variable from the current method.
     * @see sMethod#queryVariableOrConstant(String, IDataProcessorBuilder)
     *
     * @param name the variable name, can be {xxx} or normal string.
     * @return the variable object
     */
    public Variable queryVariableOrConstant(String name) {
        return GlobalContext.getCurrentMethod().queryVariableOrConstant(name, builder);
    }

    /***
     * Query the variable from the current method.
     * @see sMethod#queryVariable(String)
     *
     * @param name the variable name, can be {xxx} or normal string.
     * @return the variable object
     */
    public Variable queryVariable(String name) {
        return GlobalContext.getCurrentMethod().queryVariable(name);
    }

    /***
     * Query the variable from the current method, and convert to specify type.
     * @see sMethod#queryVariable(String, VariableType, IDataProcessorBuilder)
     *
     * @param name the variable name, can be {xxx} or normal string.
     * @return the variable object
     */
    public Variable queryVariable(String name, VariableType specType) {
        return GlobalContext.getCurrentMethod().queryVariable(name, specType, builder);
    }

    /***
     * Query the variable from the current method, and convert to specify type.
     * @see sMethod#queryVariable(String, VariableType, IDataProcessorBuilder)
     *
     * @param name the variable name, can be {xxx} or normal string.
     * @return the variable object
     */
    public Variable tryQueryVariable(String name, VariableType specType) {
        return GlobalContext.getCurrentMethod().tryQueryVariable(name, specType, builder);
    }

    public Variable tryQueryVariable(String name) {
        return GlobalContext.getCurrentMethod().tryQueryVariable(name);
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
