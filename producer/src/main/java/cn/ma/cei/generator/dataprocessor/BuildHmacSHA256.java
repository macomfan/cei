package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.DataProcessorBase;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.buildin.TheStream;
import cn.ma.cei.model.processor.xHmacSHA256;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.utils.Checker;

public class BuildHmacSHA256 extends DataProcessorBase<xHmacSHA256> {
    @Override
    public Variable build(xHmacSHA256 item, IDataProcessorBuilder builder) {
        Variable input = queryVariableOrConstant(item.input, xString.inst.getType());
        Variable key = queryVariableOrConstant(item.key);
        Variable output = createUserVariable(TheStream.getType(), item.name);
        builder.hmacsha265(output, input, key);
        return output;
    }

    @Override
    public VariableType returnType(xHmacSHA256 item) {
        return TheStream.getType();
    }

    @Override
    public String resultVariableName(xHmacSHA256 item) {
        return item.name;
    }
}
