package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.generator.*;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.model.json.xJsonParser;
import cn.ma.cei.utils.Checker;

public class JsonParser extends DataProcessorBase<xJsonParser> {
    @Override
    public Variable build(xJsonParser item, IDataProcessorBuilder builder) {
        return BuildJsonParser.build(item, getDefaultInput(), builder, item.usedFor);
    }

    @Override
    public VariableType returnType(xJsonParser item) {
        return BuilderContext.variableType(item.model);
    }

    @Override
    public String resultVariableName(xJsonParser item) {
        return null;
    }
}
