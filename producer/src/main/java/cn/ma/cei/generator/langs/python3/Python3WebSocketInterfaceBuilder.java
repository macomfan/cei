package cn.ma.cei.generator.langs.python3;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IWebSocketActionBuilder;
import cn.ma.cei.generator.builder.IWebSocketImplementationBuilder;
import cn.ma.cei.generator.builder.IWebSocketInterfaceBuilder;
import cn.ma.cei.generator.sMethod;

import java.util.List;

public class Python3WebSocketInterfaceBuilder implements IWebSocketInterfaceBuilder {
    @Override
    public IWebSocketImplementationBuilder createImplementationBuilderForTrigger() {
        return null;
    }

    @Override
    public IWebSocketImplementationBuilder createImplementationBuilderForResponse() {
        return null;
    }

    @Override
    public void setupCallback(Variable callback) {

    }

    @Override
    public void send(Variable send) {

    }

    @Override
    public IWebSocketActionBuilder createWebSocketActionBuilder() {
        return null;
    }

    @Override
    public IWebSocketImplementationBuilder createOnConnectBuilder() {
        return null;
    }

    @Override
    public void setupOnConnect(sMethod onConnect) {

    }

    @Override
    public void connect(Variable url, Variable option) {

    }

    @Override
    public void onAddReference(VariableType variableType) {

    }

    @Override
    public void startMethod(VariableType returnType, String methodDescriptor, List<Variable> params) {

    }

    @Override
    public IDataProcessorBuilder createDataProcessorBuilder() {
        return null;
    }

    @Override
    public void endMethod() {

    }
}
