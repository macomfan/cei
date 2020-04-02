package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.generator.DataProcessorBase;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.model.processor.xURLEscape;
import cn.ma.cei.model.types.xString;

public class BuildURLEscape extends DataProcessorBase<xURLEscape> {
    @Override
    public Variable build(xURLEscape item, IDataProcessorBuilder builder) {
        Variable output = createUserVariable(xString.inst.getType(), item.name);
        Variable input = queryVariableOrConstant(item.input, xString.inst.getType());
        builder.URLEscape(output, input);
        return output;
    }

    @Override
    public VariableType returnType(xURLEscape item) {
        return xString.inst.getType();
    }

    @Override
    public String resultVariableName(xURLEscape item) {
        return item.name;
    }
}
