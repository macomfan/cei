package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.Variable;

public interface IDataProcessorBuilder {
    IJsonBuilderBuilder createJsonBuilderBuilder();

    IStringBuilderBuilder createStringBuilderBuilder();

    IJsonParserBuilder createJsonParserBuilder();

    void getNow(Variable output, Variable format);

    void base64(Variable output, Variable input);

    void hmacsha265(Variable output, Variable input, Variable key);

    /***
     * Return the statement of converting the json builder to string;
     * e.g.
     * return BuilderContext.createStatement(jsonBuilder.getDescriptor() + ".toJsonString()");
     *
     * @param jsonBuilder the json builder variable
     * @return the statement variable
     */
    Variable jsonBuilderToString(Variable jsonBuilder);


    /***
     * Return the statement of calling the replacement function.
     * e.g.
     * return BuilderContext.createStatement("StringBuilder.replace(items)");
     *
     * @param items the input items
     * @return the statement variable
     */
    Variable stringReplacement(Variable... items);
}
