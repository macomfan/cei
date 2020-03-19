package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.generator.DataProcessorBase;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.model.processor.xHmacSHA256;

public class HmacSHA256 extends DataProcessorBase<xHmacSHA256> {
    @Override
    public void build(xHmacSHA256 item, IDataProcessorBuilder builder) {

    }

    @Override
    public VariableType returnType(xHmacSHA256 item, String resultVariableName) {
        return null;
    }
}
