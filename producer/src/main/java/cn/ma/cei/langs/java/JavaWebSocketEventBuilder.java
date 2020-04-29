package cn.ma.cei.langs.java;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.IMethod;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IWebSocketEventBuilder;
import cn.ma.cei.generator.builder.IWebSocketNestedBuilder;
import cn.ma.cei.langs.java.tools.JavaClass;
import cn.ma.cei.langs.java.tools.JavaMethod;

public class JavaWebSocketEventBuilder implements IWebSocketEventBuilder {

    JavaClass clientClass;
    JavaMethod method;
    JavaWebSocketNestedBuilder triggerBuilder = null;
    JavaWebSocketNestedBuilder sendBuilder = null;

    public JavaWebSocketEventBuilder(JavaClass clientClass, JavaMethod connection) {
        this.clientClass = clientClass;
        this.method = connection;
    }

    @Override
    public void startEvent() {
    }

    @Override
    public IWebSocketNestedBuilder createNestedBuilderForTrigger() {
        triggerBuilder = new JavaWebSocketNestedBuilder(clientClass);
        return triggerBuilder;
    }

    @Override
    public IWebSocketNestedBuilder createNestedBuilderForResponse() {
        sendBuilder = new JavaWebSocketNestedBuilder(clientClass);
        return sendBuilder;
    }

    @Override
    public void newEvent(Variable event) {
        method.addAssign(method.defineVariable(event), method.newInstance(event.getType()));
    }

    @Override
    public void setAsPersistentEvent(Variable event) {
        method.addInvoke(event.getDescriptor() + ".setPersistent", BuilderContext.createStatement("true"));
    }

    @Override
    public void registerEvent(Variable event) {
        method.addInvoke("this.connection.registerEvent", event);
    }

    @Override
    public void setTriggerToEvent(Variable event, IMethod trigger) {
        method.addLambda(event, "setTrigger", trigger.getInputVariableList());
        method.addCode(triggerBuilder.method.getCode());
        method.endLambda();
    }

    @Override
    public void setEventToEvent(Variable event, IMethod eventMethod) {
        method.addLambda(event, "setEvent", eventMethod.getInputVariableList());
        method.addCode(sendBuilder.method.getCode());
        method.endLambda();
    }

    @Override
    public void endEvent() {

    }
}
