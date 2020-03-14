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
        public VariableType callbackMessageType = null;
    }

    public static void build(xCallback callback, VariableType callbackMessageType, IWebSocketActionBuilder actionBuilder) {
        ActionContext context = new ActionContext();
        context.name = callback.name;
        context.response = callback.response;
        context.callbackMessageType = callbackMessageType;
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
            Variable msg = send.createInputVariable(WebSocketMessage.getType(), "msg");
            BuildWebSocketImplementation.buildSendInAction(context.send, msg, actionBuilder.createImplementationBuilderForSend());
            actionBuilder.setActionToAction(actionVariable, send);
            GlobalContext.setCurrentMethod(interfaceMethod);
        }

        if (context.response != null) {
            Variable callbackVariable = interfaceMethod.getVariable(context.name);
            sMethod response = interfaceMethod.createNestedMethod(context.name + "Response");
            GlobalContext.setCurrentMethod(response);
            // Build response
            Variable msg = response.createInputVariable(WebSocketMessage.getType(), "msg");
            BuildWebSocketImplementation.buildResponse(context.response, msg, context.callbackMessageType, callbackVariable, actionBuilder.createImplementationBuilderForSend());
            actionBuilder.setActionToAction(actionVariable, response);
            GlobalContext.setCurrentMethod(interfaceMethod);
        }

        actionBuilder.registerAction(actionVariable);
        // End action
        actionBuilder.endAction();
    }
}
