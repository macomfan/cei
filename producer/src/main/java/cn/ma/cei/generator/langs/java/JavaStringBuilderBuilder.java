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


    @Override
    public void defineStringBuilderObject(Variable stringBuilderObject) {
        method.addAssign(method.defineVariable(stringBuilderObject), method.newInstance(stringBuilderObject.getType()));
    }

    @Override
    public void appendStringItem(Variable stringBuilderObject, Variable variable) {
        method.addInvoke(stringBuilderObject.getDescriptor() + ".appendStringItem", variable);
    }

    @Override
    public void combineStringItems(Variable stringBuilderObject, Variable separator) {
        method.addInvoke(stringBuilderObject.getDescriptor() + ".combineStringItems", separator);
    }
}
