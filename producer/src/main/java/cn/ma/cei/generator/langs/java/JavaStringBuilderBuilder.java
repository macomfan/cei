package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.builder.StringBuilderBuilder;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.langs.java.tools.JavaMethod;

public class JavaStringBuilderBuilder extends StringBuilderBuilder {
    private JavaMethod method;

    public JavaStringBuilderBuilder(JavaMethod method) {
        this.method = method;
    }

    @Override
    public Variable stringReplacement(Variable... items) {
        return VariableFactory.createConstantVariable(method.invoke("StringUtils.replace", items));
    }
}
