package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.generator.DataProcessorBase;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.model.processor.xBase64;
import cn.ma.cei.model.types.xString;

public class Base64 extends DataProcessorBase<xBase64> {
    @Override
    public void build(xBase64 item, IDataProcessorBuilder builder) {

    }

    @Override
    public VariableType returnType(xBase64 item, String resultVariableName) {
        if (item.name.equals(resultVariableName)) {
            return xString.inst.getType();
        }
        return null;
    }
}
