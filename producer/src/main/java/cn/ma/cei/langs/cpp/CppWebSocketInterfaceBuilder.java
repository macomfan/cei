package cn.ma.cei.langs.cpp;

import cn.ma.cei.generator.IMethod;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IWebSocketEventBuilder;
import cn.ma.cei.generator.builder.IWebSocketInterfaceBuilder;
import cn.ma.cei.generator.builder.IWebSocketNestedBuilder;
import cn.ma.cei.langs.cpp.tools.CppClass;
import cn.ma.cei.langs.cpp.tools.CppMethod;

import java.util.List;

public class CppWebSocketInterfaceBuilder implements IWebSocketInterfaceBuilder {
    private CppMethod method = null;
    private final CppClass client;

    CppWebSocketInterfaceBuilder(CppClass client) {
        this.client = client;
    }

    @Override
    public IWebSocketNestedBuilder createOnConnectBuilder() {
        return new CppWebSocketNestedBuilder(method);
    }

    @Override
    public IWebSocketNestedBuilder createOnCloseBuilder() {
        return new CppWebSocketNestedBuilder(method);
    }

    @Override
    public IWebSocketEventBuilder createWebSocketEventBuilder() {
        return new CppWebSocketEventBuilder(client, method);
    }

    @Override
    public void onAddReference(VariableType variableType) {

    }

    @Override
    public void startMethod(VariableType returnType, String methodDescriptor, List<Variable> params) {
        method = new CppMethod(client);
    }

    @Override
    public IDataProcessorBuilder createDataProcessorBuilder() {
        return new CppDataProcessorBuilder(method);
    }

    @Override
    public void endMethod(Variable returnVariable) {

    }

    @Override
    public void setupOnConnect(Variable connection, IMethod onConnect) {

    }

    @Override
    public void connect(Variable connection, Variable target) {

    }

    @Override
    public void setupOnClose(Variable connection, IMethod onClose) {

    }

    @Override
    public void close(Variable connection) {

    }
}
