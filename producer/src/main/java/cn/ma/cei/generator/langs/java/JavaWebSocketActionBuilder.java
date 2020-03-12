package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IWebSocketActionBuilder;
import cn.ma.cei.generator.builder.IWebSocketImplementationBuilder;
import cn.ma.cei.generator.langs.java.tools.JavaClass;
import cn.ma.cei.generator.langs.java.tools.JavaMethod;
import cn.ma.cei.generator.sMethod;

public class JavaWebSocketActionBuilder implements IWebSocketActionBuilder {

    JavaClass clientClass;
    JavaMethod method;
    JavaWebSocketImplementationBuilder triggerBuilder = null;
    JavaWebSocketImplementationBuilder sendBuilder = null;

    public JavaWebSocketActionBuilder(JavaClass clientClass, JavaMethod connection) {
        this.clientClass = clientClass;
        this.method = connection;
    }

    @Override
    public void startAction() {
    }

    @Override
    public IWebSocketImplementationBuilder createImplementationBuilderForTrigger() {
        triggerBuilder = new JavaWebSocketImplementationBuilder(clientClass);
        return triggerBuilder;
    }

    @Override
    public IWebSocketImplementationBuilder createImplementationBuilderForSend() {
        sendBuilder = new JavaWebSocketImplementationBuilder(clientClass);
        return sendBuilder;
    }

    @Override
    public void newAction(Variable action) {
        method.addAssign(method.defineVariable(action), method.newInstance(action.getType()));
    }

    @Override
    public void registerAction(Variable action) {
        method.addInvoke("registerAction", action);
    }

    @Override
    public void setTriggerToAction(Variable action, sMethod trigger) {
        method.addLambda(action, "setTrigger", trigger.getInputVariableList());
        method.addCode(triggerBuilder.method.getCode());
        method.endLambda();
    }

    @Override
    public void setSendToAction(Variable action, sMethod send) {
        method.addLambda(action, "setAction", send.getInputVariableList());
        method.addCode(sendBuilder.method.getCode());
        method.endLambda();
    }

    @Override
    public void endAction() {

    }
}
