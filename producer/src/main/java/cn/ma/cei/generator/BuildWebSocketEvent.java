package cn.ma.cei.generator;

import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IWebSocketEventBuilder;
import cn.ma.cei.generator.builder.IWebSocketNestedBuilder;
import cn.ma.cei.generator.buildin.WebSocketConnection;
import cn.ma.cei.generator.buildin.WebSocketEvent;
import cn.ma.cei.generator.buildin.WebSocketMessage;
import cn.ma.cei.model.websocket.xTrigger;
import cn.ma.cei.model.websocket.xWSHandler;
import cn.ma.cei.model.websocket.xWSOnMessage;
import cn.ma.cei.model.websocket.xWSUserCallback;
import cn.ma.cei.model.xResponse;
import cn.ma.cei.utils.Checker;

public class BuildWebSocketEvent {
    private static class EventContext {
        public String name = null;
        public xTrigger trigger = null;
        public xWSHandler handler = null;
        public xResponse response = null;
        public VariableType callbackMessageType = null;
        public boolean persistent = false;
    }

    public static void build(xWSUserCallback callback, VariableType callbackMessageType, IWebSocketEventBuilder eventBuilder) {
        EventContext context = new EventContext();
        context.name = callback.callback;
        context.response = callback.handler;
        context.callbackMessageType = callbackMessageType;
        context.trigger = callback.trigger;
        context.persistent = callback.persistent;
        BuildWebSocketEvent.build(context, eventBuilder);
    }

    public static void build(xWSOnMessage event,
                             IWebSocketEventBuilder eventBuilder) {
        EventContext context = new EventContext();
        context.name = event.name;
        context.handler = event.handler;
        context.trigger = event.trigger;
        context.persistent = true;
        BuildWebSocketEvent.build(context, eventBuilder);
    }


    private static void build(EventContext context,
                              IWebSocketEventBuilder eventBuilder) {
        sMethod interfaceMethod = GlobalContext.getCurrentMethod();
        // Start event
        eventBuilder.startEvent();

        Variable eventVariable = interfaceMethod.createTempVariable(WebSocketEvent.getType(), context.name + "Event");
        eventBuilder.newEvent(eventVariable);
        if (context.persistent) {
            eventBuilder.setAsPersistentEvent(eventVariable);
        }
        if (context.trigger != null) {
            sMethod trigger = interfaceMethod.createNestedMethod(context.name + "Trigger");
            GlobalContext.setCurrentMethod(trigger);
            Variable msg = trigger.createInputVariable(WebSocketMessage.getType(), "msg");
            // Build trigger
            IWebSocketNestedBuilder nestedBuilder =
                    Checker.checkNull(eventBuilder.createNestedBuilderForTrigger(), eventBuilder, "NestedBuilderForTrigger");
            BuildWebSocketNested.buildTrigger(context.trigger, msg, nestedBuilder);
            eventBuilder.setTriggerToEvent(eventVariable, trigger);
        } else {
            // TODO
        }

        if (context.handler != null) {
            sMethod handler = interfaceMethod.createNestedMethod(context.name + "Handler");
            GlobalContext.setCurrentMethod(handler);
            // Build handler
            handler.createInputVariable(WebSocketConnection.getType(), "connection");
            Variable msg = handler.createInputVariable(WebSocketMessage.getType(), "msg");
            IWebSocketNestedBuilder nestedBuilder =
                    Checker.checkNull(eventBuilder.createNestedBuilderForResponse(), eventBuilder, "nestedBuilderForResponse");
            IDataProcessorBuilder dataProcessorBuilder = Checker.checkNull(nestedBuilder.createDataProcessorBuilder(), nestedBuilder, "DataProcessorBuilder");
            BuildDataProcessor.Context dataProcessorContext = new BuildDataProcessor.Context();
            dataProcessorContext.defaultInput = msg;
            dataProcessorContext.procedure = context.handler;
            dataProcessorContext.dataProcessorBuilder = dataProcessorBuilder;
            BuildDataProcessor.build(dataProcessorContext);
            eventBuilder.setEventToEvent(eventVariable, handler);
            GlobalContext.setCurrentMethod(interfaceMethod);
        }

        if (context.response != null) {
            Variable callbackVariable = interfaceMethod.getVariable(context.name);
            sMethod response = interfaceMethod.createNestedMethod(context.name + "Response");
            GlobalContext.setCurrentMethod(response);
            // Build response
            response.createInputVariable(WebSocketConnection.getType(), "connection");
            Variable msg = response.createInputVariable(WebSocketMessage.getType(), "msg");
            BuildWebSocketNested.buildResponse(context.response, msg, context.callbackMessageType, callbackVariable, eventBuilder.createNestedBuilderForResponse());
            eventBuilder.setEventToEvent(eventVariable, response);
            GlobalContext.setCurrentMethod(interfaceMethod);
        }
        eventBuilder.registerEvent(eventVariable);
        // End event
        eventBuilder.endEvent();
    }
}
