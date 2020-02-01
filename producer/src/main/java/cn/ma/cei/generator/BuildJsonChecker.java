package cn.ma.cei.generator;

import cn.ma.cei.generator.builder.JsonCheckerBuilder;
import cn.ma.cei.generator.builder.MethodBuilder;
import cn.ma.cei.generator.buildin.JsonChecker;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.model.json.checker.xJsonChecker;
import cn.ma.cei.model.json.checker.xJsonEqual;
import cn.ma.cei.model.json.checker.xJsonNotEqual;
import cn.ma.cei.model.json.xJsonParser;

public class BuildJsonChecker {
    static class JsonCheckContext {
        Variable jsonCheckerObject;
        JsonCheckerBuilder jsonCheckerBuilder;
        Variable key;
        Variable value;
    }

    public static void build(xJsonChecker jsonChecker, Variable jsonParser, JsonCheckerBuilder builder, MethodBuilder method) {
        Variable jsonCheckerVar = method.createLocalVariable(JsonChecker.getType(), "jsonChecker");
        builder.defineJsonChecker(jsonCheckerVar, jsonParser);
        jsonChecker.items.forEach(item -> {
            JsonCheckContext context = new JsonCheckContext();
            context.jsonCheckerObject = jsonCheckerVar;
            context.jsonCheckerBuilder = builder;

            if (item instanceof xJsonEqual) {
                ProcessEqual((xJsonEqual) item, builder);
            } else if (item instanceof xJsonNotEqual) {
                ProcessNotEqual((xJsonNotEqual) item);
            }
        });
        builder.completeJsonChecker(jsonCheckerVar);
    }

    private static void ProcessEqual(xJsonEqual jsonEqual, JsonCheckerBuilder builder) {
//        Variable key = VariableFactory.createHardcodeStringVariable(jsonEqual.key);
//        builder.setEqual();
    }

    private static void ProcessNotEqual(xJsonNotEqual jsonNotEqual) {

    }
}
