package cn.ma.cei.langs.java;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IWebSocketActionBuilder;
import cn.ma.cei.generator.builder.IWebSocketImplementationBuilder;
import cn.ma.cei.langs.java.tools.JavaClass;
import cn.ma.cei.langs.java.tools.JavaMethod;
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
    public IWebSocketImplementationBuilder createImplementationBuilderForResponse() {
        sendBuilder = new JavaWebSocketImplementationBuilder(clientClass);
        return sendBuilder;
    }

    @Override
    public void newAction(Variable action) {
        method.addAssign(method.defineVariable(action), method.newInstance(action.getType()));
    }

    @Override
    public void setAsPersistentAction(Variable action) {
        method.addInvoke(action.getDescriptor() + ".setPersistent", BuilderContext.createStatement("true"));
    }

    @Override
    public void registerAction(Variable action) {
        method.addInvoke("this.connection.registerAction", action);
    }

    @Override
    public void setTriggerToAction(Variable action, sMethod trigger) {
        method.addLambda(action, "setTrigger", trigger.getInputVariableList());
        method.addCode(triggerBuilder.method.getCode());
        method.endLambda();
    }

    @Override
    public void setActionToAction(Variable action, sMethod actionMethod) {
        method.addLambda(action, "setAction", actionMethod.getInputVariableList());
        method.addCode(sendBuilder.method.getCode());
        method.endLambda();
    }

    @Override
    public void endAction() {

    }
}
