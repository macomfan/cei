package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.generator.DataProcessorBase;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.buildin.TheStream;
import cn.ma.cei.model.processor.xEncodeHex;
import cn.ma.cei.model.processor.xHmacSHA256;
import cn.ma.cei.model.types.xString;

public class BuildEncodeHex extends DataProcessorBase<xEncodeHex> {
    @Override
    public Variable build(xEncodeHex item, IDataProcessorBuilder builder) {
        Variable input = queryVariableOrConstant(item.input, TheStream.getType());
        Variable output = createUserVariable(xString.inst.getType(), item.name);
        builder.encodeHex(output, input);
        return output;
    }

    @Override
    public VariableType returnType(xEncodeHex item) {
        return xString.inst.getType();
    }

    @Override
    public String resultVariableName(xEncodeHex item) {
        return item.name;
    }
}
