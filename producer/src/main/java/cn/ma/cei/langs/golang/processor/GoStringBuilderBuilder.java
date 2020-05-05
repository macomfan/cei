package cn.ma.cei.langs.golang.processor;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IStringBuilderBuilder;
import cn.ma.cei.langs.golang.tools.GoMethod;

public class GoStringBuilderBuilder implements IStringBuilderBuilder {
    private GoMethod method;

    public GoStringBuilderBuilder(GoMethod method) {
        this.method = method;
    }

    @Override
    public void defineStringBuilderObject(Variable stringBuilderObject) {

    }

    @Override
    public void appendStringItem(Variable stringBuilderObject, Variable variable) {

    }

    @Override
    public void combineStringItems(Variable stringBuilderObject, Variable separator) {

    }
}
