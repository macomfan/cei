package cn.ma.cei.langs.java;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IWebSocketNestedBuilder;
import cn.ma.cei.langs.java.tools.JavaClass;
import cn.ma.cei.langs.java.tools.JavaMethod;

public class JavaWebSocketNestedBuilder implements IWebSocketNestedBuilder {

    public JavaMethod method;

    public JavaWebSocketNestedBuilder(JavaClass clientClass) {
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
    public void endMethod(Variable returnVariable) {
        method.addReturn(returnVariable);
    }


//    @Override
//    public void send(Variable send) {
//        method.addInvoke("connection.send", send);
//    }
//
//    @Override
//    public void callback(Variable callbackVariable, Variable response) {
//        method.addInvoke(callbackVariable.getDescriptor() + ".invoke", response);
//    }
}
