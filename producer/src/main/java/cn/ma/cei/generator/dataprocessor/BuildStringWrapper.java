package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.generator.DataProcessorBase;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IJsonBuilderBuilder;
import cn.ma.cei.generator.builder.IJsonParserBuilder;
import cn.ma.cei.generator.builder.IStringBuilderBuilder;
import cn.ma.cei.model.string.xAddStringItem;
import cn.ma.cei.model.string.xCombineStringItems;
import cn.ma.cei.model.string.xStringBuilder;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.utils.Checker;

public class BuildStringWrapper extends DataProcessorBase<xStringBuilder> {
    @Override
    public Variable build(xStringBuilder stringBuilder, IDataProcessorBuilder builder) {
        if (Checker.isNull(stringBuilder.items)) {
            return null;
        }
//        Checker.isNull(builder, BuildJsonParser.class, "IDataProcessorBuilder");
//        IStringBuilderBuilder stringBuilder = builder.createStringBuilderBuilder();
//        Checker.isNull(stringBuilder, BuildJsonParser.class, "IJsonParserBuilder");
//
//
//        stringBuilder.items.forEach(item -> {
//            if (item instanceof xAddStringItem) {
//
//            } else if (item instanceof xCombineStringItems) {
//
//            }
//        });
        return null;
    }

    @Override
    public VariableType returnType(xStringBuilder item) {
        return xString.inst.getType();
    }

    @Override
    public String resultVariableName(xStringBuilder item) {
        return null;
    }
}
