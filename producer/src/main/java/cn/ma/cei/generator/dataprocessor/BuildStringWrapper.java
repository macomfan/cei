package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.generator.*;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IStringBuilderBuilder;
import cn.ma.cei.generator.buildin.StringWrapper;
import cn.ma.cei.model.string.xAddStringArray;
import cn.ma.cei.model.string.xAddStringItem;
import cn.ma.cei.model.string.xCombineStringItems;
import cn.ma.cei.model.string.xStringBuilder;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.model.types.xStringArray;
import cn.ma.cei.utils.Checker;

public class BuildStringWrapper extends DataProcessorBase<xStringBuilder> {
    @Override
    public Variable build(xStringBuilder stringBuilder, IDataProcessorBuilder builder) {
        if (Checker.isNull(stringBuilder.items)) {
            return null;
        }

        Checker.isNull(builder, BuildJsonParser.class, "IDataProcessorBuilder");
        IStringBuilderBuilder stringBuilderBuilder =
                Checker.checkNull(builder.createStringBuilderBuilder(), builder, "StringBuilderBuilder");
        Variable rootStringBuilder = createUserVariable(StringWrapper.getType(), stringBuilder.name);
        stringBuilderBuilder.defineStringBuilderObject(rootStringBuilder);

        stringBuilder.items.forEach(item -> {
            if (item instanceof xAddStringItem) {
                processAppendStingItem(rootStringBuilder, (xAddStringItem)item, stringBuilderBuilder);
            } else if (item instanceof xCombineStringItems) {
                processCombineString(rootStringBuilder, (xCombineStringItems)item, stringBuilderBuilder);
            } else if (item instanceof xAddStringArray) {
                processAddStingArray(rootStringBuilder, (xAddStringArray)item, stringBuilderBuilder);
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
        builder.addStringItem(rootStringBuilder, input);
    }

    public void processCombineString(Variable rootStringBuilder, xCombineStringItems combineStringItems, IStringBuilderBuilder builder) {
        Variable separator = queryVariableOrConstant(combineStringItems.separator);
        Variable prefix = queryVariableOrConstant(combineStringItems.prefix, xString.inst.getType());
        Variable suffix = queryVariableOrConstant(combineStringItems.suffix, xString.inst.getType());
        builder.combineStringItems(rootStringBuilder, prefix, suffix, separator);
    }

    public void processAddStingArray(Variable rootStringBuilder, xAddStringArray addStringArray, IStringBuilderBuilder builder) {
        Variable input = queryVariable(addStringArray.input, xStringArray.inst.getType());
        Variable trim;
        if (addStringArray.trim) {
            trim = BuilderContext.createStatement(Constant.keyword().get("true"));
        } else {
            trim = BuilderContext.createStatement(Constant.keyword().get("false"));
        }
        builder.addStringArray(rootStringBuilder, input, trim);
    }
}
