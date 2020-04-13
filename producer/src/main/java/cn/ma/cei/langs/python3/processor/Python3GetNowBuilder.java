package cn.ma.cei.langs.python3.processor;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IGetNowBuilder;
import cn.ma.cei.langs.python3.tools.Python3Method;

public class Python3GetNowBuilder implements IGetNowBuilder {

    Python3Method method;

    public Python3GetNowBuilder(Python3Method method) {
        this.method = method;
    }

    @Override
    public String convertToStringFormat(String format) {
        return null;
    }

    @Override
    public void getNow(Variable output, Variable format) {
        method.addAssign(method.defineVariable(output), method.invoke("CEIUtils.get_now", format));
    }
}
