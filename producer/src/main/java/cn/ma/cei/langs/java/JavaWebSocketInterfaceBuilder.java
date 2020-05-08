package cn.ma.cei.langs.java;

import cn.ma.cei.generator.IMethod;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IWebSocketEventBuilder;
import cn.ma.cei.generator.builder.IWebSocketInterfaceBuilder;
import cn.ma.cei.generator.builder.IWebSocketNestedBuilder;
import cn.ma.cei.langs.java.tools.JavaClass;
import cn.ma.cei.langs.java.tools.JavaMethod;

import java.util.List;

public class JavaWebSocketInterfaceBuilder implements IWebSocketInterfaceBuilder {

    JavaClass clientClass;
    JavaMethod method;

    JavaWebSocketNestedBuilder onConnectBuilder = null;
    JavaWebSocketNestedBuilder onCloseBuilder = null;

    public JavaWebSocketInterfaceBuilder(JavaClass clientClass) {
        this.clientClass = clientClass;
        this.method = new JavaMethod(clientClass);
    }

    @Override
    public void onAddReference(VariableType variableType) {

    }

    @Override
    public void startMethod(VariableType returnType, String methodDescriptor, List<Variable> params) {
        method.startMethod(returnType, methodDescriptor, params);
    }

    @Override
    public IDataProcessorBuilder createDataProcessorBuilder() {
        return new JavaDataProcessorBuilder(method);
    }

    @Override
    public void setupOnConnect(Variable connection, IMethod onConnect) {
        method.addLambda(connection.getDescriptor() + ".setOnConnect", onConnect.getInputVariableList());
        method.addCode(onConnectBuilder.method.getCode());
        method.endLambda();
    }

    @Override
    public void connect(Variable connection, Variable target) {
        method.addInvoke(connection.getDescriptor() + ".connect", target);
    }

    @Override
    public void setupOnClose(Variable connection, IMethod onClose) {
        method.addLambda(connection.getDescriptor() + ".setOnClose", onClose.getInputVariableList());
        method.addCode(onCloseBuilder.method.getCode());
        method.endLambda();
    }

    @Override
    public void close(Variable connection) {
        method.addInvoke(connection.getDescriptor() + ".close");
    }

    @Override
    public void endMethod(Variable returnVariable) {
        if (returnVariable != null) {
            method.addReturn(returnVariable);
        }
        method.endMethod();
        clientClass.addMethod(method);
    }

    @Override
    public IWebSocketEventBuilder createWebSocketEventBuilder() {
        return new JavaWebSocketEventBuilder(clientClass, method);
    }

    @Override
    public IWebSocketNestedBuilder createOnConnectBuilder() {
        onConnectBuilder = new JavaWebSocketNestedBuilder(clientClass);
        return onConnectBuilder;
    }

    @Override
    public IWebSocketNestedBuilder createOnCloseBuilder() {
        onCloseBuilder = new JavaWebSocketNestedBuilder(clientClass);
        return onCloseBuilder;
    }
}
