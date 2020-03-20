package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.DataProcessorBase;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.model.authentication.xAddQueryString;
import cn.ma.cei.utils.Checker;

public class AddQueryString extends DataProcessorBase<xAddQueryString> {
    @Override
    public Variable build(xAddQueryString item, IDataProcessorBuilder builder) {
        Variable requestVariable = queryVariable("{request}");
        if (Checker.isEmpty(item.key) || Checker.isEmpty(item.value)) {
            throw new CEIException("[BuildSignature] key and value must be defined for append_query_string");
        }
        Variable variable = queryVariable(item.value);
        Variable key = queryVariable(item.key);
        builder.addQueryString(requestVariable, key, variable);
        return null;
    }

    @Override
    public VariableType returnType(xAddQueryString item) {
        return null;
    }

    @Override
    public String resultVariableName(xAddQueryString item) {
        return null;
    }


}
