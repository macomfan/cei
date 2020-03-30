package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.*;
import cn.ma.cei.generator.langs.java.tools.JavaClass;
import cn.ma.cei.generator.langs.java.tools.JavaMethod;
import cn.ma.cei.generator.sMethod;

import java.util.List;

public class JavaWebSocketInterfaceBuilder implements IWebSocketInterfaceBuilder {

    JavaClass clientClass;
    JavaMethod method;

    JavaWebSocketImplementationBuilder triggerBuilder = null;
    JavaWebSocketImplementationBuilder responseBuilder = null;
    JavaWebSocketImplementationBuilder onConnectBuilder = null;

    public JavaWebSocketInterfaceBuilder(JavaClass clientClass) {
        this.clientClass = clientClass;
        this.method = new JavaMethod(clientClass);
    }

    @Override
    public IWebSocketImplementationBuilder createImplementationBuilderForTrigger() {
        triggerBuilder = new JavaWebSocketImplementationBuilder(clientClass);
        return triggerBuilder;
    }

    @Override
    public IWebSocketImplementationBuilder createImplementationBuilderForResponse() {
        responseBuilder = new JavaWebSocketImplementationBuilder(clientClass);
        return responseBuilder;
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
    public void endMethod() {
        method.endMethod();
        clientClass.addMethod(method);
    }

    @Override
    public void send(Variable send) {
        method.addInvoke("sendWS", send);
    }

    @Override
    public IWebSocketActionBuilder createWebSocketActionBuilder() {
        return new JavaWebSocketActionBuilder(clientClass, method);
    }

    @Override
    public IWebSocketImplementationBuilder createOnConnectBuilder() {
        onConnectBuilder = new JavaWebSocketImplementationBuilder(clientClass);
        return onConnectBuilder;
    }

    @Override
    public void setupOnConnect(sMethod onConnect) {
        method.addLambda("setOnConnect", onConnect.getInputVariableList());
        method.addCode(onConnectBuilder.method.getCode());
        method.endLambda();
    }

    @Override
    public void connect(Variable url, Variable option) {
        method.addInvoke("connectWS", url, option);
    }
}
