package cn.ma.cei.langs.java.processor;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IGetNowBuilder;
import cn.ma.cei.langs.java.tools.JavaMethod;

public class JavaGetNowBuilder implements IGetNowBuilder {

    JavaMethod method;

    public JavaGetNowBuilder(JavaMethod method) {
        this.method = method;
    }

    @Override
    public String convertToStringFormat(String format) {
        return null;
    }

    @Override
    public void getNow(Variable output, Variable format) {
        method.addAssign(method.defineVariable(output), method.invoke("CEIUtils.getNow", format));
    }
}
