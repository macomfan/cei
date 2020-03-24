package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.generator.DataProcessorBase;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.model.processor.xBase64;
import cn.ma.cei.model.types.xString;

public class BuildBase64 extends DataProcessorBase<xBase64> {
    @Override
    public Variable build(xBase64 item, IDataProcessorBuilder builder) {
        Variable input = queryVariableOrConstant(item.input, xString.inst.getType());
        Variable output = createLocalVariable(xString.inst.getType(), item.name);
        builder.base64(output, input);
        return output;
    }

    @Override
    public VariableType returnType(xBase64 item) {
        return xString.inst.getType();
    }

    @Override
    public String resultVariableName(xBase64 item) {
        return item.name;
    }
}
