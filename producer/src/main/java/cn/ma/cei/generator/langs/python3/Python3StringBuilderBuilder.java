package cn.ma.cei.generator.langs.python3;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IStringBuilderBuilder;
import cn.ma.cei.generator.langs.python3.tools.Python3Method;

public class Python3StringBuilderBuilder implements IStringBuilderBuilder {
    Python3Method method;

    public Python3StringBuilderBuilder(Python3Method method) {
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
