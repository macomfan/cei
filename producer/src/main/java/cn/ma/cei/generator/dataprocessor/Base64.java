package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.DataProcessorBase;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.model.processor.xBase64;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.utils.Checker;

public class Base64 extends DataProcessorBase<xBase64> {
    @Override
    public Variable build(xBase64 item, IDataProcessorBuilder builder) {
        if (Checker.isEmpty(item.name)) {
            throw new CEIException("[BuildSignature] output must be defined for base64");
        }
        if (Checker.isEmpty(item.input)) {
            throw new CEIException("[BuildSignature] input must be defined for base64");
        }
        Variable input = queryVariable(item.input);
        Variable output = createVariable(xString.inst.getType(), item.name);
        builder.base64(output, input);
        return output;
    }

    @Override
    public VariableType returnType(xBase64 item) {
        return xString.inst.getType();
    }

    @Override
    public String resultVariableName(xBase64 item) {
        return null;
    }
}
