package cn.ma.cei.langs.python3;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.IMethod;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IWebSocketEventBuilder;
import cn.ma.cei.generator.builder.IWebSocketNestedBuilder;
import cn.ma.cei.langs.python3.tools.Python3Class;
import cn.ma.cei.langs.python3.tools.Python3Method;

public class Python3WebSocketEventBuilder implements IWebSocketEventBuilder {

    Python3Class clientClass;
    Python3Method method;
    Python3WebSocketNestedBuilder triggerBuilder = null;
    Python3WebSocketNestedBuilder sendBuilder = null;

    public Python3WebSocketEventBuilder(Python3Class clientClass, Python3Method method) {
        this.clientClass = clientClass;
        this.method = method;
    }

    @Override
    public void startEvent() {

    }

    @Override
    public IWebSocketNestedBuilder createNestedBuilderForTrigger() {
        triggerBuilder = new Python3WebSocketNestedBuilder(clientClass);
        return triggerBuilder;
    }

    @Override
    public IWebSocketNestedBuilder createNestedBuilderForResponse() {
        sendBuilder = new Python3WebSocketNestedBuilder(clientClass);
        return sendBuilder;
    }

    @Override
    public void newEvent(Variable event, Variable isPersistent) {
        method.addAssign(method.defineVariable(event), method.newInstance(event.getType(), isPersistent));
    }

    @Override
    public void registerEvent(Variable event) {
        method.addInvoke("self.__connection.register_event", event);
    }


    @Override
    public void setTriggerToEvent(Variable event, IMethod triggerMethod) {
        Python3Method nestedMethod = new Python3Method(clientClass);
        method.getCode().endln();
        nestedMethod.startNestedMethod(null, event.getDescriptor() + "_trigger", triggerMethod.getInputVariableList());
        nestedMethod.getCode().appendCode(triggerBuilder.method.getCode());
        nestedMethod.endMethod();
        method.getCode().appendCode(nestedMethod.getCode());
        method.addInvoke(event.getDescriptor() + ".set_trigger",
                BuilderContext.createStatement(event.getDescriptor() + "_trigger"));
    }

    @Override
    public void setEventToEvent(Variable event, IMethod eventMethod) {
        Python3Method nestedMethod = new Python3Method(clientClass);
        method.getCode().endln();
        nestedMethod.startNestedMethod(null, event.getDescriptor() + "_event", eventMethod.getInputVariableList());
        nestedMethod.getCode().appendCode(sendBuilder.method.getCode());
        nestedMethod.endMethod();
        method.getCode().appendCode(nestedMethod.getCode());
        method.addInvoke(event.getDescriptor() + ".set_event",
                BuilderContext.createStatement(event.getDescriptor() + "_event"));
    }

    @Override
    public void endEvent() {

    }
}
