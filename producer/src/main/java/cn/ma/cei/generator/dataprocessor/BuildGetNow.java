package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.generator.DataProcessorBase;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.model.processor.xGetNow;
import cn.ma.cei.model.types.xString;

public class BuildGetNow extends DataProcessorBase<xGetNow> {

    @Override
    public Variable build(xGetNow item, IDataProcessorBuilder builder) {
        Variable output = createLocalVariable(xString.inst.getType(), item.name);
        Variable format = queryVariableOrConstant(item.format, xString.inst.getType());
        builder.getNow(output, format);
        return output;
    }

    @Override
    public VariableType returnType(xGetNow item) {
        return xString.inst.getType();
    }

    @Override
    public String resultVariableName(xGetNow item) {
        return item.name;
    }
}
