package cn.ma.cei.langs.python3;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IWebSocketNestedBuilder;
import cn.ma.cei.langs.python3.tools.Python3Class;
import cn.ma.cei.langs.python3.tools.Python3Method;

public class Python3WebSocketNestedBuilder implements IWebSocketNestedBuilder {
    public Python3Method method;

    public Python3WebSocketNestedBuilder(Python3Class clientClass) {
        this.method = new Python3Method(clientClass);
    }

    @Override
    public void onAddReference(VariableType variableType) {
    }

    @Override
    public IDataProcessorBuilder createDataProcessorBuilder() {
        return new Python3DataProcessorBuilder(method);
    }

    @Override
    public void endMethod(Variable returnVariable) {
        method.addReturn(returnVariable);
    }
}
