package cn.ma.cei.generator.builder;

public interface IDataProcessorBuilder {
    IJsonBuilderBuilder createJsonBuilderBuilder();

    IStringBuilderBuilder createStringBuilderBuilder();

    IJsonParserBuilder createJsonParserBuilder();
}
