package cn.ma.cei.langs.python3;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IWebSocketActionBuilder;
import cn.ma.cei.generator.builder.IWebSocketImplementationBuilder;
import cn.ma.cei.generator.builder.IWebSocketInterfaceBuilder;
import cn.ma.cei.langs.python3.tools.Python3Class;
import cn.ma.cei.langs.python3.tools.Python3Method;
import cn.ma.cei.generator.sMethod;

import java.util.List;

public class Python3WebSocketInterfaceBuilder implements IWebSocketInterfaceBuilder {

    Python3Class clientClass;
    Python3Method method;

    Python3WebSocketImplementationBuilder onConnectBuilder = null;

    public Python3WebSocketInterfaceBuilder(Python3Class clientClass) {
        this.clientClass = clientClass;
        this.method = new Python3Method(clientClass);
    }

    @Override
    public void send(Variable send) {
        method.addInvoke("self.send_ws", send);
    }

    @Override
    public IWebSocketActionBuilder createWebSocketActionBuilder() {
        return new Python3WebSocketActionBuilder(clientClass, method);
    }

    @Override
    public IWebSocketImplementationBuilder createOnConnectBuilder() {
        onConnectBuilder = new Python3WebSocketImplementationBuilder(clientClass);
        return onConnectBuilder;
    }

    @Override
    public void setupOnConnect(sMethod onConnect) {

    }

    @Override
    public void connect(Variable url, Variable option) {
        method.addInvoke("self.connect_ws", url, option);
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
    public void endMethod(Variable returnVariable) {
        method.endMethod();
        clientClass.addMethod(method);
    }
}
