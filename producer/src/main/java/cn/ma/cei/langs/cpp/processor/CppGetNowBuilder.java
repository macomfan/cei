package cn.ma.cei.langs.cpp.processor;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IGetNowBuilder;

public class CppGetNowBuilder implements IGetNowBuilder {
    @Override
    public String convertToStringFormat(String format) {
        return format;
    }

    @Override
    public void getNow(Variable output, Variable format) {

    }
}
