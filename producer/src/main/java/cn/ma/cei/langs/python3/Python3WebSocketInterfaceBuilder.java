package cn.ma.cei.langs.python3;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.IMethod;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IWebSocketEventBuilder;
import cn.ma.cei.generator.builder.IWebSocketInterfaceBuilder;
import cn.ma.cei.generator.builder.IWebSocketNestedBuilder;
import cn.ma.cei.langs.python3.tools.Python3Class;
import cn.ma.cei.langs.python3.tools.Python3Method;

import java.util.List;

public class Python3WebSocketInterfaceBuilder implements IWebSocketInterfaceBuilder {

    Python3Class clientClass;
    Python3Method method;

    Python3WebSocketNestedBuilder onConnectBuilder = null;
    Python3WebSocketNestedBuilder onCloseBuilder = null;

    public Python3WebSocketInterfaceBuilder(Python3Class clientClass) {
        this.clientClass = clientClass;
        this.method = new Python3Method(clientClass);
    }

    @Override
    public IWebSocketEventBuilder createWebSocketEventBuilder() {
        return new Python3WebSocketEventBuilder(clientClass, method);
    }

    @Override
    public IWebSocketNestedBuilder createOnConnectBuilder() {
        onConnectBuilder = new Python3WebSocketNestedBuilder(clientClass);
        return onConnectBuilder;
    }

    @Override
    public IWebSocketNestedBuilder createOnCloseBuilder() {
        onCloseBuilder = new Python3WebSocketNestedBuilder(clientClass);
        return onCloseBuilder;
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
        return new Python3DataProcessorBuilder(method);
    }

    @Override
    public void setupOnConnect(Variable connection, IMethod onConnect) {
        Python3Method nestedMethod = new Python3Method(clientClass);
        method.getCode().endln();
        nestedMethod.startNestedMethod(null, onConnect.getDescriptor() + "_event", onConnect.getInputVariableList());
        nestedMethod.getCode().appendCode(onConnectBuilder.method.getCode());
        nestedMethod.endMethod();
        method.getCode().appendCode(nestedMethod.getCode());
        method.addInvoke(connection.getDescriptor() + ".set_on_connect",
                BuilderContext.createStatement(onConnect.getDescriptor() + "_event"));
    }

    @Override
    public void connect(Variable connection, Variable target) {
        method.addInvoke(connection.getDescriptor() + ".connect", target);
    }

    @Override
    public void setupOnClose(Variable connection, IMethod onClose) {
        Python3Method nestedMethod = new Python3Method(clientClass);
        method.getCode().endln();
        nestedMethod.startNestedMethod(null, onClose.getDescriptor() + "_event", onClose.getInputVariableList());
        nestedMethod.getCode().appendCode(onCloseBuilder.method.getCode());
        nestedMethod.endMethod();
        method.getCode().appendCode(nestedMethod.getCode());
        method.addInvoke(connection.getDescriptor() + ".set_on_close",
                BuilderContext.createStatement(onClose.getDescriptor() + "_event"));
    }

    @Override
    public void close(Variable connection) {
        method.addInvoke(connection.getDescriptor() + ".close");
    }


    @Override
    public void endMethod(Variable returnVariable) {
        method.endMethod();
        clientClass.addMethod(method);
    }

    @Override
    public void endMethod() {
        method.endMethod();
        clientClass.addMethod(method);
    }
}
