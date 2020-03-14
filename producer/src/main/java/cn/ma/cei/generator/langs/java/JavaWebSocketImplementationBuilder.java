package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IWebSocketImplementationBuilder;
import cn.ma.cei.generator.langs.java.tools.JavaClass;
import cn.ma.cei.generator.langs.java.tools.JavaMethod;

public class JavaWebSocketImplementationBuilder implements IWebSocketImplementationBuilder {

    public JavaMethod method;

    public JavaWebSocketImplementationBuilder(JavaClass clientClass) {
        method = new JavaMethod(clientClass);
    }

    @Override
    public void onAddReference(VariableType variableType) {

    }

    @Override
    public IDataProcessorBuilder createDataProcessorBuilder() {
        return new JavaDataProcessorBuilder(method);
    }


    @Override
    public void startTrigger() {

    }

    @Override
    public void endTrigger() {

    }

    @Override
    public void send(Variable send) {
        method.addInvoke("send", send);
    }

    @Override
    public void callback(Variable callbackVariable, Variable response) {
        method.addInvoke(callbackVariable.getDescriptor() + ".invoke", response);
    }
}
