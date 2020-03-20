package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.model.base.xDataProcessorItem;
import cn.ma.cei.utils.RegexHelper;

public abstract class DataProcessorBase<T extends xDataProcessorItem> {
    private Variable defaultInput = null;

    public Variable callBuild(xDataProcessorItem item, Variable defaultInput, IDataProcessorBuilder builder) {
        if (item != null) {
            this.defaultInput = defaultInput;
            Variable result = build(convertTo(item), builder);
            this.defaultInput = null;
            return result;
        }
        return null;
    }

    public VariableType callReturnType(xDataProcessorItem item) {
        if (item !=null) {
            return returnType(convertTo(item));
        }
        return null;
    }

    public String callResultVariableName(xDataProcessorItem item) {
        if (item !=null) {
            return resultVariableName(convertTo(item));
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private T convertTo(xDataProcessorItem item) {
        return (T) item;
    }


    public Variable createVariable(VariableType type, String name) {
        return null;
    }

    public Variable getDefaultInput() {
        return defaultInput;
    }

    public Variable queryVariable(String name) {
        Variable variable = GlobalContext.getCurrentMethod().getVariableAsParam(name);
        if (variable == null) {
            Variable options = GlobalContext.getCurrentMethod().getVariable("options");
            if (options == null) {
                throw new CEIException("[BuildSignature] Cannot query option");
            }
            String variableName = RegexHelper.isReference(name);
            variable = options.getMember(variableName);
            if (variable == null) {
                throw new CEIException("[BuildSignature] cannot query variable: " + name);
            }
        }
        return variable;
    }

    public abstract Variable build(T item, IDataProcessorBuilder builder);

    public abstract VariableType returnType(T item);

    public abstract String resultVariableName(T item);
}
