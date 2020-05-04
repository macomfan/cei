package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.generator.DataProcessorBase;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.model.processor.xWebSocketNullChecker;

public class BuildWebSocketNullChecker extends DataProcessorBase<xWebSocketNullChecker> {
    @Override
    public Variable build(xWebSocketNullChecker item, IDataProcessorBuilder builder) {
        return null;
    }

    @Override
    public VariableType returnType(xWebSocketNullChecker item) {
        return null;
    }

    @Override
    public String resultVariableName(xWebSocketNullChecker item) {
        return null;
    }
}
