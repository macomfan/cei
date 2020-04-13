package cn.ma.cei.langs.python3;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IWebSocketActionBuilder;
import cn.ma.cei.generator.builder.IWebSocketImplementationBuilder;
import cn.ma.cei.langs.python3.tools.Python3Class;
import cn.ma.cei.langs.python3.tools.Python3Method;
import cn.ma.cei.generator.sMethod;

public class Python3WebSocketActionBuilder implements IWebSocketActionBuilder {

    Python3Class clientClass;
    Python3Method method;
    Python3WebSocketImplementationBuilder triggerBuilder = null;
    Python3WebSocketImplementationBuilder sendBuilder = null;

    public Python3WebSocketActionBuilder(Python3Class clientClass, Python3Method method) {
        this.clientClass = clientClass;
        this.method = method;
    }

    @Override
    public void startAction() {

    }

    @Override
    public IWebSocketImplementationBuilder createImplementationBuilderForTrigger() {
        triggerBuilder = new Python3WebSocketImplementationBuilder(clientClass);
        return triggerBuilder;
    }

    @Override
    public IWebSocketImplementationBuilder createImplementationBuilderForResponse() {
        sendBuilder = new Python3WebSocketImplementationBuilder(clientClass);
        return sendBuilder;
    }

    @Override
    public void newAction(Variable action) {
        //method.startMethod(null, action.getDescriptor(), null);
        method.addAssign(method.defineVariable(action), method.newInstance(action.getType()));
    }

    @Override
    public void setAsPersistentAction(Variable action) {
        method.addInvoke(action.getDescriptor() + ".set_persistent", BuilderContext.createStatement("True"));
    }

    @Override
    public void registerAction(Variable action) {
        method.addInvoke("self.register_action", action);
    }


    @Override
    public void setTriggerToAction(Variable action, sMethod triggerMethod) {
        Python3Method nestedMethod = new Python3Method(clientClass);
        method.getCode().endln();
        nestedMethod.startNestedMethod(null, action.getDescriptor() + "_trigger", triggerMethod.getInputVariableList());
        nestedMethod.getCode().appendCode(triggerBuilder.method.getCode());
        nestedMethod.endMethod();
        method.getCode().appendCode(nestedMethod.getCode());
        method.addInvoke(action.getDescriptor() + ".set_trigger",
                BuilderContext.createStatement(action.getDescriptor() + "_trigger"));
    }

    @Override
    public void setActionToAction(Variable action, sMethod actionMethod) {
        Python3Method nestedMethod = new Python3Method(clientClass);
        method.getCode().endln();
        nestedMethod.startNestedMethod(null, action.getDescriptor() + "_action", actionMethod.getInputVariableList());
        nestedMethod.getCode().appendCode(sendBuilder.method.getCode());
        nestedMethod.endMethod();
        method.getCode().appendCode(nestedMethod.getCode());
        method.addInvoke(action.getDescriptor() + ".set_action",
                BuilderContext.createStatement(action.getDescriptor() + "_action"));
    }

    @Override
    public void endAction() {

    }
}
