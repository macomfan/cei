package cn.ma.cei.langs.cpp;

import cn.ma.cei.generator.IMethod;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IWebSocketEventBuilder;
import cn.ma.cei.generator.builder.IWebSocketNestedBuilder;
import cn.ma.cei.langs.cpp.tools.CppClass;
import cn.ma.cei.langs.cpp.tools.CppMethod;

public class CppWebSocketEventBuilder implements IWebSocketEventBuilder {
    private CppClass clientClass;
    private CppMethod method;

    public CppWebSocketEventBuilder(CppClass clientClass, CppMethod method) {
        this.clientClass = clientClass;
        this.method = method;
    }

    @Override
    public void startEvent() {

    }

    @Override
    public IWebSocketNestedBuilder createNestedBuilderForTrigger() {
        return new CppWebSocketNestedBuilder(method);
    }

    @Override
    public IWebSocketNestedBuilder createNestedBuilderForResponse() {
        return new CppWebSocketNestedBuilder(method);
    }

    @Override
    public void newEvent(Variable event, Variable isPersistent) {

    }

    @Override
    public void registerEvent(Variable connection, Variable event) {

    }

    @Override
    public void setTriggerToEvent(Variable event, IMethod triggerMethod) {

    }

    @Override
    public void setEventToEvent(Variable event, IMethod eventMethod) {

    }

    @Override
    public void endEvent() {

    }
}
