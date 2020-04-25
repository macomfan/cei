package cn.ma.cei.generator;

import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IJsonCheckerBuilder;
import cn.ma.cei.generator.builder.IWebSocketImplementationBuilder;
import cn.ma.cei.model.json.xJsonParser;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.model.websocket.xSend;
import cn.ma.cei.model.websocket.xTrigger;
import cn.ma.cei.model.xProcedure;
import cn.ma.cei.model.xResponse;
import cn.ma.cei.utils.Checker;

import java.util.LinkedList;

public class BuildWebSocketImplementation {
    public static void buildTrigger(xTrigger trigger, Variable msg, IWebSocketImplementationBuilder builder) {
        Checker.isNull(builder, BuildWebSocketImplementation.class, "WebSocketImplementationBuilder");
        if (trigger.jsonChecker != null) {
            IDataProcessorBuilder dataProcessorBuilder = builder.createDataProcessorBuilder();
            xJsonParser jsonParser = new xJsonParser();
            jsonParser.itemList = null;
            jsonParser.jsonChecker = trigger.jsonChecker;
            jsonParser.jsonChecker.usedFor = IJsonCheckerBuilder.UsedFor.RETURN_RESULT;
            xProcedure procedure = new xProcedure();
            procedure.items = new LinkedList<>();
            procedure.items.add(jsonParser);
            BuildDataProcessor.build(procedure, msg, "", dataProcessorBuilder);
        }
    }

    public static void buildSendInAction(xSend send, Variable msg, IWebSocketImplementationBuilder builder) {
        Checker.isNull(builder, BuildWebSocketImplementation.class, "WebSocketImplementationBuilder");
        IDataProcessorBuilder dataProcessorBuilder = Checker.checkNull(builder.createDataProcessorBuilder(), builder, "DataProcessorBuilder");
        BuildDataProcessor.Context context = new BuildDataProcessor.Context();
        context.defaultInput = msg;
        context.procedure = send;
        context.returnVariableName = send.value;
        context.specifiedReturnType = xString.inst.getType();
        context.dataProcessorBuilder = dataProcessorBuilder;
        Variable sendVariable = BuildDataProcessor.build(context);
//        IDataProcessorBuilder dataProcessorBuilder = Checker.checkNull(builder.createDataProcessorBuilder(), builder, "DataProcessorBuilder");
//        Variable sendVariable = BuildDataProcessor.build(send, msg, send.value, dataProcessorBuilder);
        builder.send(sendVariable);
    }

    public static void buildResponse(xResponse response, Variable msg, VariableType callbackType, Variable callbackVariable, IWebSocketImplementationBuilder builder) {
        Checker.isNull(builder, BuildWebSocketImplementation.class, "WebSocketImplementationBuilder");
        Variable result = BuildResponse.build(response, msg, callbackType, builder);
        builder.callback(callbackVariable, result);
    }
}
