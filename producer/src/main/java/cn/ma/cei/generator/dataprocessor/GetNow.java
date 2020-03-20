package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.DataProcessorBase;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.model.processor.xGetNow;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.utils.Checker;

public class GetNow extends DataProcessorBase<xGetNow> {

    @Override
    public Variable build(xGetNow item, IDataProcessorBuilder builder) {
        if (Checker.isEmpty(item.name)) {
            throw new CEIException("[BuildSignature] output must be defined for get_now");
        }
        Variable output = createVariable(xString.inst.getType(), item.name);
        // Variable output = sMethod.createLocalVariable(xString.inst.getType(), RegexHelper.isReference(item.name));
        Variable format = queryVariable(item.format);
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
