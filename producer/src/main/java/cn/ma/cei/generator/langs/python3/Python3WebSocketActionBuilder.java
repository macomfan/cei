package cn.ma.cei.generator.langs.python3;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IWebSocketActionBuilder;
import cn.ma.cei.generator.builder.IWebSocketImplementationBuilder;
import cn.ma.cei.generator.sMethod;

public class Python3WebSocketActionBuilder implements IWebSocketActionBuilder {
    @Override
    public void startAction() {

    }

    @Override
    public IWebSocketImplementationBuilder createImplementationBuilderForTrigger() {
        return null;
    }

    @Override
    public IWebSocketImplementationBuilder createImplementationBuilderForSend() {
        return null;
    }

    @Override
    public void newAction(Variable action) {

    }

    @Override
    public void registerAction(Variable action) {

    }

    @Override
    public void setTriggerToAction(Variable action, sMethod triggerMethod) {

    }

    @Override
    public void setActionToAction(Variable action, sMethod actionMethod) {

    }

    @Override
    public void endAction() {

    }
}
