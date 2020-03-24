package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.generator.*;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.buildin.AuthenticationTool;
import cn.ma.cei.model.authentication.xCombineQueryString;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.utils.Checker;

public class BuildCombineQueryString extends DataProcessorBase<xCombineQueryString> {
    @Override
    public Variable build(xCombineQueryString item, IDataProcessorBuilder builder) {
        Variable output = createLocalVariable(xString.inst.getType(), item.name);
        Variable requestVariable = queryVariable("{request}");

        Variable sort;
        if (!Checker.isEmpty(item.sort)) {
            sort = BuilderContext.createStatement(xString.inst.getType(), Constant.authenticationMethod().tryGet(item.sort));
        } else {
            sort = BuilderContext.createStatement(xString.inst.getType(), Constant.authenticationMethod().tryGet(AuthenticationTool.Constant.NONE));
        }
        Variable separator = BuilderContext.createStringConstant(item.separator);
        builder.combineQueryString(requestVariable, output, sort, separator);
        return output;
    }

    @Override
    public VariableType returnType(xCombineQueryString item) {
        return xString.inst.getType();
    }

    @Override
    public String resultVariableName(xCombineQueryString item) {
        return item.name;
    }
}
