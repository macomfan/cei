package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.builder.StringBuilderBuilder;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.langs.java.tools.JavaMethod;

public class JavaStringBuilderBuilder extends StringBuilderBuilder {
    private JavaMethod method;

    public JavaStringBuilderBuilder(JavaMethod method) {
        this.method = method;
    }

    @Override
    public Variable stringReplacement(Variable... items) {
        return BuilderContext.createStatement(method.invoke("StringUtils.replace", items));
    }
}
