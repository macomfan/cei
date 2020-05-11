package cn.ma.cei.langs.golang.processor;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IStringBuilderBuilder;
import cn.ma.cei.langs.golang.tools.GoMethod;

public class GoStringBuilderBuilder implements IStringBuilderBuilder {
    private final GoMethod method;

    public GoStringBuilderBuilder(GoMethod method) {
        this.method = method;
    }

    @Override
    public void defineStringBuilderObject(Variable stringBuilderObject) {
        method.addAssignAndDeclare(stringBuilderObject.getDescriptor(), method.createInstance(stringBuilderObject.getType()));
    }

    @Override
    public void addStringItem(Variable stringBuilderObject, Variable variable) {
        method.addInvoke(stringBuilderObject.getDescriptor() + ".AppendStringItem", method.var(variable));
    }

    @Override
    public void combineStringItems(Variable stringBuilderObject, Variable prefix, Variable suffix, Variable separator) {
        method.addInvoke(stringBuilderObject.getDescriptor() + ".CombineStringItems",
                method.var(prefix),
                method.var(suffix),
                method.var(separator));
    }

    @Override
    public void addStringArray(Variable stringBuilderObject, Variable input, Variable trim) {
        method.addInvoke(stringBuilderObject.getDescriptor() + ".AddStringArray", method.var(input), method.var(trim));
    }
}
