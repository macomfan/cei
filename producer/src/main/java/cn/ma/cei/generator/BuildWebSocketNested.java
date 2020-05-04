package cn.ma.cei.generator;

import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IJsonCheckerBuilder;
import cn.ma.cei.generator.builder.IWebSocketNestedBuilder;
import cn.ma.cei.generator.buildin.Keyword;
import cn.ma.cei.model.json.xJsonParser;
import cn.ma.cei.model.processor.xWebSocketCallback;
import cn.ma.cei.model.websocket.xTrigger;
import cn.ma.cei.model.xProcedure;
import cn.ma.cei.model.xResponse;
import cn.ma.cei.utils.Checker;

import java.util.LinkedList;

public class BuildWebSocketNested {
    public static void buildTrigger(xTrigger trigger, Variable msg, IWebSocketNestedBuilder builder) {
        Checker.isNull(builder, BuildWebSocketNested.class, "WebSocketNestedBuilder");
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
        } else if (trigger.nullChecker != null) {
            builder.endMethod(BuilderContext.createStatement(Constant.keyword().get(Keyword.TRUE)));
        }
    }

    public static void buildResponse(xResponse response, Variable msg, VariableType callbackType, Variable callbackVariable, IWebSocketNestedBuilder builder) {
        IDataProcessorBuilder dataProcessorBuilder = Checker.checkNull(builder.createDataProcessorBuilder(), builder, "DataProcessorBuilder");
        Variable result = BuildResponse.build(response, msg, callbackType, builder);
        xProcedure procedure = new xProcedure();
        procedure.items = new LinkedList<>();
        xWebSocketCallback newCallback = new xWebSocketCallback();
        newCallback.func = "{" + callbackVariable.getName() + "}";
        procedure.items.add(newCallback);
        BuildDataProcessor.Context context = new BuildDataProcessor.Context();
        context.procedure = procedure;
        context.defaultInput = result;
        context.dataProcessorBuilder = dataProcessorBuilder;
        BuildDataProcessor.build(context);
    }
}
