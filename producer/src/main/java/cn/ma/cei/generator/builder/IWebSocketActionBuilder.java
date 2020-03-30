package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.sMethod;

public interface IWebSocketActionBuilder {
    void startAction();

    IWebSocketImplementationBuilder createImplementationBuilderForTrigger();

    IWebSocketImplementationBuilder createImplementationBuilderForResponse();

    void newAction(Variable action);

    void registerPersistentAction(Variable action);

    void registerDisposableAction(Variable action);

    void setTriggerToAction(Variable action, sMethod triggerMethod);

    void setActionToAction(Variable action, sMethod actionMethod);

    void endAction();
}
