package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.IMethod;
import cn.ma.cei.generator.Variable;

public interface IWebSocketEventBuilder extends IBuilderBase {
    void startEvent();

    IWebSocketNestedBuilder createNestedBuilderForTrigger();

    IWebSocketNestedBuilder createNestedBuilderForResponse();

    void newEvent(Variable event, Variable isPersistent);

    void registerEvent(Variable connection, Variable event);

    void setTriggerToEvent(Variable event, IMethod triggerMethod);

    void setEventToEvent(Variable event, IMethod eventMethod);

    void endEvent();
}

