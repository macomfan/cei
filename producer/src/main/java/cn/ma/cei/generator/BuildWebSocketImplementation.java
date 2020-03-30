package cn.ma.cei.generator;

import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IJsonCheckerBuilder;
import cn.ma.cei.generator.builder.IWebSocketImplementationBuilder;
import cn.ma.cei.model.base.xDataProcessorItem;
import cn.ma.cei.model.json.xJsonParser;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.model.websocket.xSend;
import cn.ma.cei.model.websocket.xTrigger;
import cn.ma.cei.model.xResponse;
import cn.ma.cei.utils.Checker;

import java.util.LinkedList;
import java.util.List;

public class BuildWebSocketImplementation {
    public static void buildTrigger(xTrigger trigger, Variable msg, IWebSocketImplementationBuilder builder) {
        Checker.isNull(builder, BuildWebSocketImplementation.class, "WebSocketImplementationBuilder");
        if (trigger.jsonChecker != null) {
            IDataProcessorBuilder dataProcessorBuilder = builder.createDataProcessorBuilder();
            xJsonParser jsonParser = new xJsonParser();
            jsonParser.itemList = null;
            jsonParser.jsonChecker = trigger.jsonChecker;
            jsonParser.jsonChecker.usedFor = IJsonCheckerBuilder.UsedFor.RETURN_RESULT;
            List<xDataProcessorItem> items = new LinkedList<>();
            items.add(jsonParser);
            BuildDataProcessor.build(items, msg, "", dataProcessorBuilder);
        }
    }

    public static void buildSendInAction(xSend send, Variable msg, IWebSocketImplementationBuilder builder) {
        Checker.isNull(builder, BuildWebSocketImplementation.class, "WebSocketImplementationBuilder");
        // Variable sendVariable = BuildUserProcedure.createValueFromAttribute("value", send, builder);
        Variable sendVariable = BuildUserProcedure.createValueFromProcedure(xString.inst.getType(), send.value, send, builder);
        builder.send(sendVariable);
    }

    public static void buildResponse(xResponse response, Variable msg, VariableType callbackType, Variable callbackVariable, IWebSocketImplementationBuilder builder) {
        Checker.isNull(builder, BuildWebSocketImplementation.class, "WebSocketImplementationBuilder");
        Variable result = BuildResponse.build(response, msg, callbackType, builder.createDataProcessorBuilder());
        builder.callback(callbackVariable, result);
    }
}
