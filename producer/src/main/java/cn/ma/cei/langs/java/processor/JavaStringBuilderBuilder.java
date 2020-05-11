package cn.ma.cei.langs.java.processor;

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
    public void addStringItem(Variable stringBuilderObject, Variable variable) {
        method.addInvoke(stringBuilderObject.getDescriptor() + ".appendStringItem", variable);
    }

    @Override
    public void combineStringItems(Variable stringBuilderObject, Variable prefix, Variable suffix, Variable separator) {
        method.addInvoke(stringBuilderObject.getDescriptor() + ".combineStringItems", prefix, suffix, separator);
    }

    @Override
    public void addStringArray(Variable stringBuilderObject, Variable input, Variable trim) {
        method.addInvoke(stringBuilderObject.getDescriptor() + ".addStringArray", input, trim);
    }
}
