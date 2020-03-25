package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.DataProcessorBase;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IJsonBuilderBuilder;
import cn.ma.cei.generator.builder.IJsonParserBuilder;
import cn.ma.cei.generator.builder.IStringBuilderBuilder;
import cn.ma.cei.generator.buildin.StringWrapper;
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

        Checker.isNull(builder, BuildJsonParser.class, "IDataProcessorBuilder");
        IStringBuilderBuilder stringBuilderBuilder = builder.createStringBuilderBuilder();
        Checker.isNull(stringBuilderBuilder, BuildJsonParser.class, "IStringBuilderBuilder");
        Variable rootStringBuilder = createLocalVariable(StringWrapper.getType(), stringBuilder.name);
        stringBuilderBuilder.defineStringBuilderObject(rootStringBuilder);

        stringBuilder.items.forEach(item -> {
            if (item instanceof xAddStringItem) {
                processAppendStingItem(rootStringBuilder, (xAddStringItem)item, stringBuilderBuilder);
            } else if (item instanceof xCombineStringItems) {
                processCombineString(rootStringBuilder, (xCombineStringItems)item, stringBuilderBuilder);
            }
        });
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

    public void processAppendStingItem(Variable rootStringBuilder, xAddStringItem addStringItem, IStringBuilderBuilder builder) {
        Variable input = queryVariableOrConstant(addStringItem.input);
        builder.appendStringItem(rootStringBuilder, input);
    }

    public void processCombineString(Variable rootStringBuilder, xCombineStringItems combineStringItems, IStringBuilderBuilder builder) {
        Variable separator = queryVariableOrConstant(combineStringItems.separator);
        builder.combineStringItems(rootStringBuilder, separator);
    }
}
