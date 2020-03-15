package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.Variable;

public interface IDataProcessorBuilder {
    IJsonBuilderBuilder createJsonBuilderBuilder();

    IStringBuilderBuilder createStringBuilderBuilder();

    IJsonParserBuilder createJsonParserBuilder();

    Variable jsonBuilderToString(Variable jsonBuilder);

    Variable stringReplacement(Variable... items);
}
