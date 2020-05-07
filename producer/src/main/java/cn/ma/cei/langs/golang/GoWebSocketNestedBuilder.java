package cn.ma.cei.langs.golang;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IWebSocketNestedBuilder;
import cn.ma.cei.langs.golang.tools.GoMethod;
import cn.ma.cei.langs.golang.tools.GoStruct;

public class GoWebSocketNestedBuilder implements IWebSocketNestedBuilder {

    private GoMethod method;

    public GoWebSocketNestedBuilder(GoStruct clientStruct) {
        method = new GoMethod(clientStruct);
    }

    @Override
    public void onAddReference(VariableType variableType) {
        method.addReference(variableType);
    }

    @Override
    public IDataProcessorBuilder createDataProcessorBuilder() {
        return new GoDataProcessorBuilder(method);
    }

    @Override
    public void endMethod(Variable returnVariable) {
        if (returnVariable != null) {
            method.addReturn(method.var(returnVariable));
        }
    }
}
