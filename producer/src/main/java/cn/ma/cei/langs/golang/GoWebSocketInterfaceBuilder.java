package cn.ma.cei.langs.golang;

import cn.ma.cei.generator.IMethod;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IWebSocketEventBuilder;
import cn.ma.cei.generator.builder.IWebSocketInterfaceBuilder;
import cn.ma.cei.generator.builder.IWebSocketNestedBuilder;
import cn.ma.cei.generator.buildin.WebSocketCallback;
import cn.ma.cei.langs.golang.tools.GoMethod;
import cn.ma.cei.langs.golang.tools.GoStruct;
import cn.ma.cei.langs.golang.vars.GoType;
import cn.ma.cei.langs.golang.vars.GoVar;

import java.util.LinkedList;
import java.util.List;

public class GoWebSocketInterfaceBuilder implements IWebSocketInterfaceBuilder {

    private final GoStruct clientStruct;
    private GoMethod method;
    private IWebSocketNestedBuilder onConnectBuilder = null;
    private IWebSocketNestedBuilder onCloseBuilder = null;

    public GoWebSocketInterfaceBuilder(GoStruct clientStruct) {
        this.clientStruct = clientStruct;
    }

    @Override
    public IWebSocketNestedBuilder createOnConnectBuilder() {
        onConnectBuilder = new GoWebSocketNestedBuilder(clientStruct);
        return onConnectBuilder;
    }

    @Override
    public IWebSocketNestedBuilder createOnCloseBuilder() {
        onCloseBuilder = new GoWebSocketNestedBuilder(clientStruct);
        return onCloseBuilder;
    }

    @Override
    public IWebSocketEventBuilder createWebSocketEventBuilder() {
        return new GoWebSocketEventBuilder(clientStruct, method);
    }

    @Override
    public void onAddReference(VariableType variableType) {
        method.addReference(variableType);
    }

    @Override
    public void startMethod(VariableType returnType, String methodDescriptor, List<Variable> params) {
        method = new GoMethod(clientStruct);
        List<GoVar> tmp = new LinkedList<>();
        params.forEach(item -> {
            if (item.getType().isBased(WebSocketCallback.getType())) {

            } else {
                tmp.add(method.var(item));
            }
        });
        GoType type = null;
        if (returnType != null) {
            type = new GoType(returnType);
        }
        method.startInterface(type, methodDescriptor, tmp);
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
        method.endMethod();
        clientStruct.addMethod(method);
    }

    @Override
    public void setupOnConnect(Variable connection, IMethod onConnect) {

    }

    @Override
    public void connect(Variable connection, Variable target) {
        method.addInvoke(connection.getDescriptor() + ".Connect", method.var(target));
    }

    @Override
    public void setupOnClose(Variable connection, IMethod onClose) {

    }

    @Override
    public void close(Variable connection) {
        method.addInvoke(connection.getDescriptor() + ".Close");
    }
}
