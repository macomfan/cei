package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.*;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.buildin.AuthenticationTool;
import cn.ma.cei.model.authentication.xCombineQueryString;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.RegexHelper;

public class CombineQueryString extends DataProcessorBase<xCombineQueryString> {
    @Override
    public Variable build(xCombineQueryString item, IDataProcessorBuilder builder) {
        if (Checker.isEmpty(item.output)) {
            throw new CEIException("[BuildSignature] output must be defined for CombineQueryString");
        }
        Variable output = createVariable(xString.inst.getType(), item.output);
        Variable requestVariable = queryVariable("{request}");

        Variable sort;
        if (!Checker.isEmpty(item.sort)) {
            sort = BuilderContext.createStatement(Constant.authenticationMethod().tryGet(item.sort));
        } else {
            sort = BuilderContext.createStatement(Constant.authenticationMethod().tryGet(AuthenticationTool.Constant.NONE));
        }
        Variable separator = queryVariable(item.separator);
        builder.combineQueryString(requestVariable, output, sort, separator);
        return output;
    }

    @Override
    public VariableType returnType(xCombineQueryString item) {
        return xString.inst.getType();
    }

    @Override
    public String resultVariableName(xCombineQueryString item) {
        return item.output;
    }
}
