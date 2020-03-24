package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.DataProcessorBase;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.model.authentication.xAddQueryString;
import cn.ma.cei.model.types.xString;

public class BuildAddQueryString extends DataProcessorBase<xAddQueryString> {
    @Override
    public Variable build(xAddQueryString item, IDataProcessorBuilder builder) {
        Variable requestVariable = queryVariable("{request}");
        Variable variable = queryVariableOrConstant(item.value, xString.inst.getType());
        Variable key = queryVariableOrConstant(item.key, xString.inst.getType());
        builder.addQueryString(requestVariable, key, variable);
        return null;
    }

    @Override
    public VariableType returnType(xAddQueryString item) {
        return VariableType.VOID;
    }

    @Override
    public String resultVariableName(xAddQueryString item) {
        return "";
    }


}
