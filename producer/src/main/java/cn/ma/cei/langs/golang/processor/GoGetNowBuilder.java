package cn.ma.cei.langs.golang.processor;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IGetNowBuilder;
import cn.ma.cei.langs.golang.tools.GoMethod;
import cn.ma.cei.langs.golang.tools.GoVar;

public class GoGetNowBuilder implements IGetNowBuilder {

    GoMethod method;

    public GoGetNowBuilder(GoMethod method) {
        this.method = method;
    }

    @Override
    public String convertToStringFormat(String format) {
        return format;
    }

    @Override
    public void getNow(Variable output, Variable format) {
        method.addAssignAndDeclare(method.useVariable(new GoVar(output)), method.invoke("authentication.GetNow", new GoVar(format)));
    }
}
