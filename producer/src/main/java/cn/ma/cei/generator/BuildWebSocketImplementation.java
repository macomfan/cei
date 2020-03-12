package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.IJsonCheckerBuilder;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IWebSocketImplementationBuilder;
import cn.ma.cei.model.json.xJsonParser;
import cn.ma.cei.model.websocket.xSend;
import cn.ma.cei.model.websocket.xTrigger;
import cn.ma.cei.model.xResponse;

public class BuildWebSocketImplementation {
    public static void buildTrigger(xTrigger trigger, Variable msg, IWebSocketImplementationBuilder builder) {
        if (builder == null) {
            throw new CEIException("WebSocketImplementationBuilder is null");
        }
        builder.startTrigger();
        {
            if (trigger.jsonChecker != null) {
                IDataProcessorBuilder implementationBuilder = builder.createDataProcessorBuilder();
                xJsonParser jsonParser = new xJsonParser();
                jsonParser.itemList = null;
                jsonParser.jsonChecker = trigger.jsonChecker;
                BuildJsonParser.build(jsonParser, msg, null, implementationBuilder, builder, IJsonCheckerBuilder.UsedFor.RETURN_RESULT);
            }
        }
        builder.endTrigger();
    }

    public static void buildSendInAction(xSend send, Variable connection, IWebSocketImplementationBuilder builder) {
        if (builder == null) {
            throw new CEIException("WebSocketImplementationBuilder is null");
        }
        Variable sendVariable = BuildAttributeExtension.createValueFromAttribute("value", send, builder);
        builder.send(connection, sendVariable);
    }

    public static void buildResponse(xResponse response, IWebSocketImplementationBuilder builder) {
        if (builder == null) {
            throw new CEIException("WebSocketImplementationBuilder is null");
        }
    }
}
