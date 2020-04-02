package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.*;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.buildin.CEIUtils;
import cn.ma.cei.model.authentication.xGetRequestInfo;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.utils.Checker;

public class BuildGetRequestInfo extends DataProcessorBase<xGetRequestInfo> {
    @Override
    public Variable build(xGetRequestInfo item, IDataProcessorBuilder builder) {
        if (Checker.isEmpty(item.name)) {
            throw new CEIException("[BuildSignature] output must be defined for GetRequestInfo");
        }
        if (Checker.isEmpty(item.info)) {
            throw new CEIException("[BuildSignature] info must be defined for GetRequestInfo");
        }
        Variable output = createUserVariable(xString.inst.getType(), item.name);
        Variable requestVariable = queryVariable("{request}");

        Variable info;
        if (!Checker.isEmpty(item.info)) {
            info = BuilderContext.createStatement(Constant.authenticationMethod().tryGet(item.info));
        } else {
            info = BuilderContext.createStatement(Constant.authenticationMethod().tryGet(CEIUtils.Constant.NONE));
        }
        Variable convert;
        if (!Checker.isEmpty(item.convert)) {
            convert = BuilderContext.createStatement(Constant.authenticationMethod().tryGet(item.convert));
        } else {
            convert = BuilderContext.createStatement(Constant.authenticationMethod().tryGet(CEIUtils.Constant.NONE));
        }
        builder.getRequestInfo(requestVariable, output, info, convert);
        return output;
    }

    @Override
    public VariableType returnType(xGetRequestInfo item) {
        return xString.inst.getType();
    }

    @Override
    public String resultVariableName(xGetRequestInfo item) {
        return item.name;
    }
}
