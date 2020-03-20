package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.DataProcessorBase;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.buildin.TheStream;
import cn.ma.cei.model.processor.xHmacSHA256;
import cn.ma.cei.utils.Checker;

public class HmacSHA256 extends DataProcessorBase<xHmacSHA256> {
    @Override
    public Variable build(xHmacSHA256 item, IDataProcessorBuilder builder) {
        if (Checker.isEmpty(item.output)) {
            throw new CEIException("[BuildSignature] output must be defined for hmacsha256");
        }
        if (Checker.isEmpty(item.input)) {
            throw new CEIException("[BuildSignature] input must be defined for hmacsha256");
        }
        if (Checker.isEmpty(item.key)) {
            throw new CEIException("[BuildSignature] key must be defined for hmacsha256");
        }
        Variable input = queryVariable(item.input);
        Variable key = queryVariable(item.key);
        Variable output = createVariable(TheStream.getType(), item.output);
        builder.hmacsha265(output, input, key);
        return output;
    }

    @Override
    public VariableType returnType(xHmacSHA256 item) {
        return TheStream.getType();
    }

    @Override
    public String resultVariableName(xHmacSHA256 item) {
        return item.output;
    }
}
