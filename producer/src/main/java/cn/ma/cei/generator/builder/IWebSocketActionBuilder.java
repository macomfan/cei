package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.sMethod;

public interface IWebSocketActionBuilder {
    void startAction();

    IWebSocketImplementationBuilder createImplementationBuilderForTrigger();

    IWebSocketImplementationBuilder createImplementationBuilderForSend();

    void newAction(Variable action);

    void registerAction(Variable action);

    void setTriggerToAction(Variable action, sMethod trigger);

    void setSendToAction(Variable action, sMethod send);

    void endAction();
}
