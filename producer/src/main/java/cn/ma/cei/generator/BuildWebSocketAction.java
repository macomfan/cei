package cn.ma.cei.generator;

import cn.ma.cei.generator.builder.IWebSocketActionBuilder;
import cn.ma.cei.generator.buildin.WebSocketAction;
import cn.ma.cei.generator.buildin.WebSocketConnection;
import cn.ma.cei.generator.buildin.WebSocketMessage;
import cn.ma.cei.model.websocket.xAction;
import cn.ma.cei.model.websocket.xCallback;
import cn.ma.cei.model.websocket.xSend;
import cn.ma.cei.model.websocket.xTrigger;
import cn.ma.cei.model.xResponse;
import cn.ma.cei.utils.Checker;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class BuildWebSocketAction {
    private static class ActionContext {
        public String name = null;
        public xTrigger trigger = null;
        public xResponse response = null;
        public xSend send = null;
    }

    public static void build(xCallback callback, IWebSocketActionBuilder actionBuilder) {
        ActionContext context = new ActionContext();
        context.name = callback.name;
        context.response = callback.response;
        context.trigger = callback.trigger;
        BuildWebSocketAction.build(context, actionBuilder);
    }

    public static void build(xAction action,
                             IWebSocketActionBuilder actionBuilder) {
        ActionContext context = new ActionContext();
        context.name = action.name;
        context.send = action.send;
        context.trigger = action.trigger;
        BuildWebSocketAction.build(context, actionBuilder);
    }

    public static void build(ActionContext context,
                             IWebSocketActionBuilder actionBuilder) {
        Checker.isNull(actionBuilder, BuildWebSocketAction.class, "WebSocketActionBuilder");

        sMethod interfaceMethod = GlobalContext.getCurrentMethod();
        // Start action
        actionBuilder.startAction();

        Variable actionVariable = interfaceMethod.createTempVariable(WebSocketAction.getType(), context.name + "Action");
        if (context.trigger != null) {
            actionBuilder.newAction(actionVariable);
            sMethod trigger = interfaceMethod.createNestedMethod(context.name + "Trigger");
            GlobalContext.setCurrentMethod(trigger);
            Variable msg = trigger.createInputVariable(WebSocketMessage.getType(), "msg");
            // Build trigger
            BuildWebSocketImplementation.buildTrigger(context.trigger, msg, actionBuilder.createImplementationBuilderForTrigger());
            actionBuilder.setTriggerToAction(actionVariable, trigger);
        } else {
            // TODO
        }

        if (context.send != null) {
            sMethod send = interfaceMethod.createNestedMethod(context.name + "Send");
            GlobalContext.setCurrentMethod(send);
            // Build send
            Variable connectionVariable = send.createInputVariable(WebSocketConnection.getType(), "connection");
            BuildWebSocketImplementation.buildSendInAction(context.send, connectionVariable, actionBuilder.createImplementationBuilderForSend());
            actionBuilder.setSendToAction(actionVariable, send);
            GlobalContext.setCurrentMethod(interfaceMethod);
        }

        actionBuilder.registerAction(actionVariable);
        // End action
        actionBuilder.endAction();
    }
}
