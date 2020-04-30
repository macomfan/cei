package cn.ma.cei.langs.java;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IStringBuilderBuilder;
import cn.ma.cei.langs.java.tools.JavaMethod;

public class JavaStringBuilderBuilder implements IStringBuilderBuilder {
    private final JavaMethod method;

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
