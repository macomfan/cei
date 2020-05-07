package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.generator.*;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.buildin.CEIUtils;
import cn.ma.cei.generator.buildin.RestfulRequest;
import cn.ma.cei.model.processor.xGetRequestInfo;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.utils.Checker;

public class BuildGetRequestInfo extends DataProcessorBase<xGetRequestInfo> {
    @Override
    public Variable build(xGetRequestInfo item, IDataProcessorBuilder builder) {
        Variable output = createUserVariable(xString.inst.getType(), item.name);
        Variable requestVariable = queryInputVariable(item.input, "{request}", RestfulRequest.getType());

        Variable info;
        if (!Checker.isEmpty(item.info)) {
            info = BuilderContext.createStatement(Constant.requestInfo().tryGet(item.info));
        } else {
            info = BuilderContext.createStatement(Constant.requestInfo().tryGet(CEIUtils.Constant.NONE));
        }
        Variable convert;
        if (!Checker.isEmpty(item.convert)) {
            convert = BuilderContext.createStatement(Constant.requestInfo().tryGet(item.convert));
        } else {
            convert = BuilderContext.createStatement(Constant.requestInfo().tryGet(CEIUtils.Constant.NONE));
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
