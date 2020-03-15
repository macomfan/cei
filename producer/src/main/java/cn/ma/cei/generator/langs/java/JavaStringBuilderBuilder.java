package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.builder.IStringBuilderBuilder;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.langs.java.tools.JavaMethod;

public class JavaStringBuilderBuilder implements IStringBuilderBuilder {
    private JavaMethod method;

    public JavaStringBuilderBuilder(JavaMethod method) {
        this.method = method;
    }


}
