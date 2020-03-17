package cn.ma.cei.generator.langs.python3;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IJsonBuilderBuilder;
import cn.ma.cei.generator.builder.IJsonParserBuilder;
import cn.ma.cei.generator.builder.IStringBuilderBuilder;
import cn.ma.cei.generator.langs.python3.tools.Python3Method;

public class Python3DataProcessorBuilder implements IDataProcessorBuilder {
    Python3Method method;

    public Python3DataProcessorBuilder(Python3Method method) {
        this.method = method;
    }

    @Override
    public IJsonBuilderBuilder createJsonBuilderBuilder() {
        return new Python3JsonBuilderBuilder(method);
    }

    @Override
    public IStringBuilderBuilder createStringBuilderBuilder() {
        return null;
    }

    @Override
    public IJsonParserBuilder createJsonParserBuilder() {
        return new Python3JsonParserBuilder(method);
    }

    @Override
    public Variable jsonBuilderToString(Variable jsonBuilder) {
        return BuilderContext.createStatement(jsonBuilder.getDescriptor() + ".to_json_string()");
    }

    @Override
    public Variable stringReplacement(Variable... items) {
        return BuilderContext.createStatement(method.invoke("StringBuilder.replace", items));
    }
}
