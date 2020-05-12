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
    private GoWebSocketNestedBuilder onConnectBuilder = null;
    private GoWebSocketNestedBuilder onCloseBuilder = null;

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
        GoType type = null;
        if (returnType != null) {
            type = new GoType(returnType);
        }
        method.startMethod(type, false, methodDescriptor, method.varListToList(params));
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
        method.addLambda(method.var(connection),
                "SetOnConnect",
                method.varListToPtrList(onConnect.getInputVariableList()),
                null);
        method.getCode().appendCode(onConnectBuilder.method.getCode());
        method.endLambda();
    }

    @Override
    public void connect(Variable connection, Variable target) {
        method.addInvoke(connection.getDescriptor() + ".Connect", method.var(target));
    }

    @Override
    public void setupOnClose(Variable connection, IMethod onClose) {
        method.addLambda(method.var(connection),
                "SetOnClose",
                method.varListToPtrList(onClose.getInputVariableList()),
                null);
        method.getCode().appendCode(onCloseBuilder.method.getCode());
        method.endLambda();
    }

    @Override
    public void close(Variable connection) {
        method.addInvoke(connection.getDescriptor() + ".Close");
    }
}
