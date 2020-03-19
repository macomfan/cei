package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.generator.BuildJsonParser;
import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.DataProcessorBase;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.model.json.xJsonParser;
import cn.ma.cei.utils.Checker;

public class JsonParser extends DataProcessorBase<xJsonParser> {
    @Override
    public void build(xJsonParser item, IDataProcessorBuilder builder) {
        BuildJsonParser.build(item, getDefaultInput(), null, builder, item.usedFor);
    }

    @Override
    public VariableType returnType(xJsonParser item, String resultVariableName) {
        if (Checker.isEmpty(resultVariableName)) {
            return BuilderContext.variableType(item.model);
        } else if (resultVariableName.equals(item.name)) {
            return BuilderContext.variableType(item.model);
        } else {
            return null;
        }
    }
}
