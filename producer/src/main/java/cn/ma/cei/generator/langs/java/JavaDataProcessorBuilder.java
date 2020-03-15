package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IJsonBuilderBuilder;
import cn.ma.cei.generator.builder.IJsonParserBuilder;
import cn.ma.cei.generator.builder.IStringBuilderBuilder;
import cn.ma.cei.generator.langs.java.tools.JavaMethod;

import java.nio.Buffer;

public class JavaDataProcessorBuilder implements IDataProcessorBuilder {
    JavaMethod method;

    public JavaDataProcessorBuilder(JavaMethod method) {
        this.method = method;
    }

    @Override
    public IJsonBuilderBuilder createJsonBuilderBuilder() {
        return new JavaJsonBuilderBuilder(method);
    }

    @Override
    public IStringBuilderBuilder createStringBuilderBuilder() {
        return null;
    }

    @Override
    public IJsonParserBuilder createJsonParserBuilder() {
        return new JavaJsonParserBuilder(method);
    }

    @Override
    public Variable jsonBuilderToString(Variable jsonBuilder) {
        return BuilderContext.createStatement(jsonBuilder.getDescriptor() + ".toJsonString()");
    }

    @Override
    public Variable stringReplacement(Variable... items) {
        return BuilderContext.createStatement(method.invoke("StringBuilder.replace", items));
    }
}
