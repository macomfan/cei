package cn.ma.cei.langs.golang;

import cn.ma.cei.generator.IMethod;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IWebSocketEventBuilder;
import cn.ma.cei.generator.builder.IWebSocketNestedBuilder;
import cn.ma.cei.langs.golang.tools.GoMethod;
import cn.ma.cei.langs.golang.tools.GoStruct;
import cn.ma.cei.langs.golang.vars.GoType;

public class GoWebSocketEventBuilder implements IWebSocketEventBuilder {
    private GoWebSocketNestedBuilder triggerBuilder = null;
    private GoWebSocketNestedBuilder responseBuilder = null;

    public GoStruct clientStruct;
    public GoMethod method;

    public GoWebSocketEventBuilder(GoStruct clientStruct, GoMethod method) {
        this.clientStruct = clientStruct;
        this.method = method;
    }

    @Override
    public void startEvent() {

    }

    @Override
    public IWebSocketNestedBuilder createNestedBuilderForTrigger() {
        triggerBuilder = new GoWebSocketNestedBuilder(clientStruct);
        return triggerBuilder;
    }

    @Override
    public IWebSocketNestedBuilder createNestedBuilderForResponse() {
        responseBuilder = new GoWebSocketNestedBuilder(clientStruct);
        return responseBuilder;
    }

    @Override
    public void newEvent(Variable event, Variable isPersistent) {
        method.addAssignAndDeclare(method.useVariable(method.var(event)), method.invoke("impl.NewWebSocketEvent", method.var(isPersistent)));
    }

    @Override
    public void registerEvent(Variable connection, Variable event) {
        method.addInvoke(connection.getDescriptor() + ".RegisterEvent", method.var(event));
    }

    @Override
    public void setTriggerToEvent(Variable event, IMethod triggerMethod) {
        method.addLambda(
                method.var(event),
                "SetTrigger",
                method.varListToPtrList(triggerMethod.getInputVariableList()),
                new GoType(triggerMethod.getReturnType()));
        method.getCode().appendCode(triggerBuilder.method.getCode());
        method.endLambda();
    }

    @Override
    public void setEventToEvent(Variable event, IMethod eventMethod) {
        method.addLambda(
                method.var(event),
                "SetEvent",
                method.varListToPtrList(eventMethod.getInputVariableList()),
                new GoType(eventMethod.getReturnType()));
        method.getCode().appendCode(responseBuilder.method.getCode());
        method.endLambda();
    }

    @Override
    public void endEvent() {

    }
}
