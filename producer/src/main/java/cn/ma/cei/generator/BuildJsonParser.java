package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.JsonCheckerBuilder;
import cn.ma.cei.generator.builder.JsonParserBuilder;
import cn.ma.cei.generator.builder.MethodBuilder;
import cn.ma.cei.generator.buildin.JsonChecker;
import cn.ma.cei.generator.buildin.JsonWrapper;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.model.json.*;
import cn.ma.cei.model.json.checker.xJsonChecker;
import cn.ma.cei.model.types.*;

public class BuildJsonParser {

    public static Variable build(xJsonParser jsonParser,
                                 Variable responseVariable,
                                 VariableType outputModelType,
                                 JsonParserBuilder jsonParserBuilder,
                                 MethodBuilder method,
                                 JsonCheckerBuilder.UsedFor usedFor) {
        if (jsonParser == null) {
            throw new CEIException("[BuildJsonParser] The root is not json parser");
        }
        Variable rootJsonObject = defineRootJsonObject(method);
        jsonParserBuilder.defineRootJsonObject(rootJsonObject, responseVariable);

        Variable model = method.createLocalVariable(outputModelType, outputModelType.getDescriptor() + "Var");
        jsonParserBuilder.defineModel(model);

        jsonParser.itemList.forEach((item) -> {
            JsonItemContext newContext = new JsonItemContext();
            newContext.jsonItem = item;
            newContext.parentModel = model;
            newContext.parentJsonObject = rootJsonObject;
            newContext.jsonParserBuilder = jsonParserBuilder;
            newContext.method = method;
            item.startBuilding();
            if (item instanceof xJsonChecker) {
                buildJsonChecker((xJsonChecker)item, newContext, usedFor);
            } else {
                processJsonItem(newContext);
            }
            item.endBuilding();
        });
        return model;
    }

    private static void buildJsonChecker(xJsonChecker jsonChecker, JsonItemContext context, JsonCheckerBuilder.UsedFor usedFor) {
        JsonCheckerBuilder jsonCheckerBuilder = context.jsonParserBuilder.createJsonCheckerBuilder();
        if (jsonCheckerBuilder == null) {
            throw new CEIException("[BuildJsonParser] JsonChecker build is null");
        }
        jsonCheckerBuilder.setUsedFor(usedFor);
        BuildJsonChecker.build(jsonChecker, context.parentJsonObject, jsonCheckerBuilder, context.method);
    }

    private static Variable getToVariable(Variable parentModel, xJsonType jsonItem) {
        if (jsonItem.to != null && !jsonItem.to.equals("")) {
            String variableName = VariableFactory.isReference(jsonItem.to);
            if (variableName == null) {
                throw new CEIException("[BuildJsonParser] To must be Variable");
            }
            return parentModel.queryMember(variableName);
        } else if (jsonItem.copy != null && !jsonItem.copy.equals("")) {
            return parentModel.queryMember(jsonItem.copy);
        }
        return null;
    }

    private static void processJsonAuto(Variable to, JsonItemContext context) {
        if (to.getType().equalTo(xString.typeName)) {
            processJsonString(to, context);
        } else if (to.getType().equalTo(xInt.typeName)) {
            processJsonInt(to, context);
        } else if (to.getType().equalTo(xBoolean.typeName)) {
            processJsonBoolean(to, context);
        } else if (to.getType().equalTo(xDecimal.typeName)) {
            processJsonDecimal(to, context);
        } else if (to.getType().equalTo(xStringArray.typeName)) {
            processJsonStringArray(to, context);
        } else if (to.getType().isCustomModel()) {
            if (to.getType().isCustomModelArray()) {
                processJsonObjectArray(to, context);
            } else {
                processJsonObject(to, context);
            }
        }
    }

    private static void processJsonItem(JsonItemContext context) {
        if (context.jsonItem == null || context.method == null || context.jsonParserBuilder == null) {
            throw new CEIException("[BuildJsonParser] null");
        }
        if (context.jsonItem.copy == null && context.jsonItem.from == null && context.jsonItem.to == null) {
            throw new CEIException("[BuildJsonParser] from, to and copy cannot null");
        } else if (context.jsonItem.copy != null && (context.jsonItem.from != null || context.jsonItem.to != null)) {
            throw new CEIException("[BuildJsonParser] from, to cannot exist with copy");
        } else if (context.jsonItem.copy != null) {
            context.jsonItem.from = context.jsonItem.copy;
            context.jsonItem.to = "{" + context.jsonItem.copy + "}";
            context.jsonItem.copy = null;
        }

        Variable to = getToVariable(context.parentModel, context.jsonItem);

        if (context.jsonItem instanceof xJsonAuto) {
            processJsonAuto(to, context);
        } else if (context.jsonItem instanceof xJsonObject) {
            processJsonObject(to, context);
        } else if (context.jsonItem instanceof xJsonObjectArray) {
            processJsonObjectArray(to, context);
        } else {
            if (context.parentModel == null || context.parentJsonObject == null) {
                throw new CEIException("[BuildJsonParser] error for normal json item");
            }
            if (context.jsonItem instanceof xJsonString) {
                processJsonString(to, context);
            } else if (context.jsonItem instanceof xJsonInteger) {
                processJsonInt(to, context);
            } else if (context.jsonItem instanceof xJsonBoolean) {
                processJsonBoolean(to, context);
            } else if (context.jsonItem instanceof xJsonDecimal) {
                processJsonDecimal(to, context);
            } else if (context.jsonItem instanceof xJsonStringArray) {
                processJsonStringArray(to, context);
            }
        }
    }

    private static Variable defineRootJsonObject(MethodBuilder method) {
        return method.createLocalVariable(JsonWrapper.getType(), "rootObj");
    }

    private static Variable defineJsonObject(JsonItemContext context) {
        xJsonWithModel jsonWithModel = (xJsonWithModel) context.jsonItem;
        return context.method.createLocalVariable(JsonWrapper.getType(), jsonWithModel.from + "Obj");
    }

    private static Variable defineModel(JsonItemContext context) {
        xJsonWithModel jsonWithModel = (xJsonWithModel) context.jsonItem;
        if (jsonWithModel.model == null || "".equals(jsonWithModel.model)) {
            throw new CEIException("[BuildJsonParser] Model is not defined");
        }
        Variable model = context.method.createTempVariable(VariableFactory.variableType(jsonWithModel.model), jsonWithModel.model + "Var");
        context.jsonParserBuilder.defineModel(model);
        return model;
    }

    private static void processJsonString(Variable to, JsonItemContext context) {
        Variable from = VariableFactory.createHardcodeStringVariable(context.jsonItem.from);
        context.jsonParserBuilder.getJsonString(to, context.parentJsonObject, from);
    }

    private static void processJsonInt(Variable to, JsonItemContext context) {
        Variable from = VariableFactory.createHardcodeStringVariable(context.jsonItem.from);
        context.jsonParserBuilder.getJsonInteger(to, context.parentJsonObject, from);
    }

    private static void processJsonDecimal(Variable to, JsonItemContext context) {
        Variable from = VariableFactory.createHardcodeStringVariable(context.jsonItem.from);
        context.jsonParserBuilder.getJsonDecimal(to, context.parentJsonObject, from);
    }

    private static void processJsonBoolean(Variable to, JsonItemContext context) {
        Variable from = VariableFactory.createHardcodeStringVariable(context.jsonItem.from);
        context.jsonParserBuilder.getJsonBoolean(to, context.parentJsonObject, from);
    }

    private static void processJsonStringArray(Variable to, JsonItemContext context) {
        Variable from = VariableFactory.createHardcodeStringVariable(context.jsonItem.from);
        context.jsonParserBuilder.getJsonStringArray(to, context.parentJsonObject, from);
    }

    private static void processJsonObjectArray(Variable to, JsonItemContext context) {
        xJsonWithModel jsonWithModel = (xJsonWithModel) context.jsonItem;
        Variable newJsonObject = context.parentJsonObject;

        Variable eachItemJsonObject = context.method.createTempVariable(JsonWrapper.getType(), "item");
        Variable from = VariableFactory.createHardcodeStringVariable(jsonWithModel.from);
        context.jsonParserBuilder.startJsonObjectArray(eachItemJsonObject, newJsonObject, from);

        Variable newModel = defineModel(context);

        jsonWithModel.itemList.forEach((item) -> {
            JsonItemContext newContext = new JsonItemContext();
            newContext.jsonItem = item;
            newContext.parentModel = newModel;
            newContext.parentJsonObject = eachItemJsonObject;
            newContext.jsonParserBuilder = context.jsonParserBuilder;
            newContext.method = context.method;
            processJsonItem(newContext);
        });

        context.jsonParserBuilder.endJsonObjectArray(to, newModel);
    }

    private static void processJsonObject(Variable to, JsonItemContext context) {
        xJsonWithModel jsonWithModel = (xJsonWithModel) context.jsonItem;
        Variable newJsonObject = defineJsonObject(context);

        Variable newModel = context.parentModel;
        if (jsonWithModel.model != null) {
            newModel = defineModel(context);
        }

        Variable from = VariableFactory.createHardcodeStringVariable(jsonWithModel.from);
        context.jsonParserBuilder.startJsonObject(newJsonObject, context.parentJsonObject, from);

        for (xJsonType item : jsonWithModel.itemList) {
            JsonItemContext newContext = new JsonItemContext();
            newContext.jsonItem = item;
            newContext.parentModel = newModel;
            newContext.parentJsonObject = newJsonObject;
            newContext.jsonParserBuilder = context.jsonParserBuilder;
            newContext.method = context.method;
            processJsonItem(newContext);
        }
        context.jsonParserBuilder.endJsonObject(to, newModel);
    }

    static class JsonItemContext {

        xJsonType jsonItem = null;
        Variable parentModel = null;
        Variable parentJsonObject = null;
        JsonParserBuilder jsonParserBuilder = null;
        MethodBuilder method = null;
    }

}
