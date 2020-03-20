package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.*;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.buildin.AuthenticationTool;
import cn.ma.cei.model.authentication.xGetRequestInfo;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.utils.Checker;

public class GetRequestInfo extends DataProcessorBase<xGetRequestInfo> {
    @Override
    public Variable build(xGetRequestInfo item, IDataProcessorBuilder builder) {
        if (Checker.isEmpty(item.output)) {
            throw new CEIException("[BuildSignature] output must be defined for GetRequestInfo");
        }
        if (Checker.isEmpty(item.info)) {
            throw new CEIException("[BuildSignature] info must be defined for GetRequestInfo");
        }
        Variable output = createVariable(xString.inst.getType(), item.output);
        Variable requestVariable = queryVariable("{request}");

        Variable info;
        if (!Checker.isEmpty(item.info)) {
            info = BuilderContext.createStatement(Constant.authenticationMethod().tryGet(item.info));
        } else {
            info = BuilderContext.createStatement(Constant.authenticationMethod().tryGet(AuthenticationTool.Constant.NONE));
        }
        Variable convert;
        if (!Checker.isEmpty(item.convert)) {
            convert = BuilderContext.createStatement(Constant.authenticationMethod().tryGet(item.convert));
        } else {
            convert = BuilderContext.createStatement(Constant.authenticationMethod().tryGet(AuthenticationTool.Constant.NONE));
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
        return item.output;
    }
}
