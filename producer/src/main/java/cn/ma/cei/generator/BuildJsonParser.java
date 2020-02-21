package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.JsonCheckerBuilder;
import cn.ma.cei.generator.builder.IJsonParserBuilder;
import cn.ma.cei.generator.builder.IMethodBuilder;
import cn.ma.cei.generator.buildin.JsonWrapper;
import cn.ma.cei.model.json.*;
import cn.ma.cei.model.json.checker.xJsonChecker;
import cn.ma.cei.model.types.*;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.RegexHelper;

public class BuildJsonParser {
    static class JsonItemContext {

        xJsonType jsonItem = null;
        Variable parentModel = null;
        Variable parentJsonObject = null;
        IJsonParserBuilder jsonParserBuilder = null;
    }


    public static Variable build(xJsonParser jsonParser,
                                 Variable responseVariable,
                                 VariableType outputModelType,
                                 IJsonParserBuilder jsonParserBuilder,
                                 IMethodBuilder method,
                                 JsonCheckerBuilder.UsedFor usedFor) {
        if (jsonParser == null) {
            throw new CEIException("[BuildJsonParser] The root is not json parser");
        }
        Variable rootJsonObject = defineRootJsonObject();
        jsonParserBuilder.defineRootJsonObject(rootJsonObject, responseVariable);

        if ((jsonParser.itemList == null || jsonParser.itemList.isEmpty()) && jsonParser.jsonChecker == null) {
            throw new CEIException("[BuildJsonParser] No any item in json parser");
        }

        if (jsonParser.jsonChecker != null) {
            jsonParser.jsonChecker.startBuilding();
            buildJsonChecker(jsonParser.jsonChecker, jsonParserBuilder, rootJsonObject, usedFor);
            jsonParser.jsonChecker.endBuilding();
        }

        if (jsonParser.itemList.isEmpty() || outputModelType == null) {
            return null;
        }
        Variable model = GlobalContext.getCurrentMethod().createTempVariable(outputModelType, outputModelType.getDescriptor() + "Var");
        jsonParserBuilder.defineModel(model);

        jsonParser.itemList.forEach(item -> {
            JsonItemContext newContext = new JsonItemContext();
            newContext.jsonItem = item;
            newContext.parentModel = model;
            newContext.parentJsonObject = rootJsonObject;
            newContext.jsonParserBuilder = jsonParserBuilder;
            processJsonItem(newContext);
        });
        return model;
    }

    private static Variable defineRootJsonObject() {
        return GlobalContext.getCurrentMethod().createTempVariable(JsonWrapper.getType(), "rootObj");
    }

    private static void buildJsonChecker(
            xJsonChecker jsonChecker,
            IJsonParserBuilder jsonParserBuilder,
            Variable rootJsonObject,
            JsonCheckerBuilder.UsedFor usedFor) {
        JsonCheckerBuilder jsonCheckerBuilder = jsonParserBuilder.createJsonCheckerBuilder();
        if (jsonCheckerBuilder == null) {
            throw new CEIException("[BuildJsonParser] JsonChecker build is null");
        }
        BuildJsonChecker.build(jsonChecker, rootJsonObject, jsonCheckerBuilder, usedFor);

    }

    private static void processJsonItem(JsonItemContext context) {
        context.jsonItem.startBuilding();
        if (context.jsonItem == null || context.jsonParserBuilder == null) {
            throw new CEIException("[BuildJsonParser] null");
        }
        if (context.jsonItem.copy == null && context.jsonItem.key == null && context.jsonItem.value == null) {
            throw new CEIException("[BuildJsonParser] key, value and copy cannot be null");
        } else if (context.jsonItem.copy != null && (context.jsonItem.key != null || context.jsonItem.value != null)) {
            throw new CEIException("[BuildJsonParser] key, value cannot exist with copy");
        } else if (context.jsonItem.copy != null) {
            context.jsonItem.key = context.jsonItem.copy;
            context.jsonItem.value = "{" + context.jsonItem.copy + "}";
            context.jsonItem.copy = null;
        }

        Variable value = getValueVariable(context.parentModel, context.jsonItem);

        if (context.jsonItem instanceof xJsonValue) {
            processJsonValue(value, context);
        } else if (context.jsonItem instanceof xJsonObject) {
            processJsonObject(value, context);
        } else if (context.jsonItem instanceof xJsonObjectArray) {
            processJsonObjectArray(value, context);
        } else {
            if (context.parentModel == null || context.parentJsonObject == null) {
                throw new CEIException("[BuildJsonParser] error for normal json item");
            }
            if (value == null) {
                throw new CEIException("[BuildJsonParser] value cannot be null for basic type");
            }
            if (context.jsonItem instanceof xJsonString) {
                processJsonString(value, context);
            } else if (context.jsonItem instanceof xJsonInteger) {
                processJsonInt(value, context);
            } else if (context.jsonItem instanceof xJsonBoolean) {
                processJsonBoolean(value, context);
            } else if (context.jsonItem instanceof xJsonDecimal) {
                processJsonDecimal(value, context);
            } else if (context.jsonItem instanceof xJsonStringArray) {
                processJsonStringArray(value, context);
            } else if (context.jsonItem instanceof xJsonDecimalArray) {
                processJsonDecimalArray(value, context);
            } else if (context.jsonItem instanceof xJsonIntArray) {
                processJsonIntArray(value, context);
            } else if (context.jsonItem instanceof xJsonBooleanArray) {
                processJsonBooleanArray(value, context);
            }
        }
        context.jsonItem.endBuilding();
    }

    private static void processJsonValue(Variable value, JsonItemContext context) {
        if (value == null) {
            throw new CEIException("[BuildJsonParser] value cannot be null for basic type");
        }
        if (value.getType().equalTo(xString.typeName)) {
            processJsonString(value, context);
        } else if (value.getType().equalTo(xInt.typeName)) {
            processJsonInt(value, context);
        } else if (value.getType().equalTo(xBoolean.typeName)) {
            processJsonBoolean(value, context);
        } else if (value.getType().equalTo(xDecimal.typeName)) {
            processJsonDecimal(value, context);
        } else if (value.getType().equalTo(xStringArray.typeName)) {
            processJsonStringArray(value, context);
        } else if (value.getType().equalTo(xDecimalArray.typeName)) {
            processJsonDecimalArray(value, context);
        } else if (value.getType().equalTo(xBooleanArray.typeName)) {
            processJsonBooleanArray(value, context);
        } else if (value.getType().equalTo(xIntArray.typeName)) {
            processJsonIntArray(value, context);
        } else {
            throw new CEIException("[BuildJsonParser] json_value only support the basic type");
        }
    }

    private static void processJsonObject(Variable value, JsonItemContext context) {
        xJsonWithModel jsonWithModel = (xJsonWithModel) context.jsonItem;
        Variable key = getKeyVariable(jsonWithModel.key);
        Variable newJsonObject = defineJsonObject(context);
        Variable newModel = defineModel(context);
        context.jsonParserBuilder.defineJsonObject(newJsonObject, context.parentJsonObject, key);

        for (xJsonType item : jsonWithModel.itemList) {
            JsonItemContext newContext = new JsonItemContext();
            newContext.jsonItem = item;
            newContext.parentModel = newModel;
            newContext.parentJsonObject = newJsonObject;
            newContext.jsonParserBuilder = context.jsonParserBuilder;
            processJsonItem(newContext);
        }
        if (value != null) {
            context.jsonParserBuilder.assignModel(value, newModel);
        }
    }

    private static void processJsonObjectArray(Variable to, JsonItemContext context) {
        xJsonWithModel jsonWithModel = (xJsonWithModel) context.jsonItem;
        Variable newJsonObject = defineJsonObject(context);
        if (!Checker.isEmpty(jsonWithModel.key)) {
            Variable key = getKeyVariable(jsonWithModel.key);
            context.jsonParserBuilder.defineJsonObject(newJsonObject, context.parentJsonObject, key);
        }
        Variable eachItemJsonObject = GlobalContext.getCurrentMethod().createTempVariable(JsonWrapper.getType(), "item");
        context.jsonParserBuilder.startJsonObjectArray(eachItemJsonObject, newJsonObject);
        Variable newModel = defineModel(context);

        jsonWithModel.itemList.forEach((item) -> {
            JsonItemContext newContext = new JsonItemContext();
            newContext.jsonItem = item;
            newContext.parentModel = newModel;
            newContext.parentJsonObject = eachItemJsonObject;
            newContext.jsonParserBuilder = context.jsonParserBuilder;
            processJsonItem(newContext);
        });

        context.jsonParserBuilder.endJsonObjectArray(to, newModel);
    }

    private static Variable defineJsonObject(JsonItemContext context) {
        xJsonWithModel jsonWithModel = (xJsonWithModel) context.jsonItem;
        if (Checker.isEmpty(jsonWithModel.key)) {
            // If key is null, use the parent json object
            return context.parentJsonObject;
        }
        return GlobalContext.getCurrentMethod().createLocalVariable(JsonWrapper.getType(), jsonWithModel.key + "Obj");
    }

    private static Variable defineModel(JsonItemContext context) {
        xJsonWithModel jsonWithModel = (xJsonWithModel) context.jsonItem;
        if (Checker.isEmpty(jsonWithModel.model)) {
            // If model is null, use the parent model
            return context.parentModel;
        }
        Variable model = GlobalContext.getCurrentMethod().createTempVariable(GlobalContext.variableType(jsonWithModel.model), jsonWithModel.model + "Var");
        context.jsonParserBuilder.defineModel(model);
        return model;
    }

    private static Variable getValueVariable(Variable parentModel, xJsonType jsonItem) {
        if (!Checker.isEmpty(jsonItem.value)) {
            String variableName = RegexHelper.isReference(jsonItem.value);
            if (variableName == null) {
                throw new CEIException("[BuildJsonParser] To must be Variable");
            }
            return parentModel.getMember(variableName);
        } else {
            return null;
        }
    }

    private static Variable getKeyVariable(String key) {
        if (Checker.isEmpty(key)) {
            throw new CEIException("[BuildJsonParser] Key cannot be null");
        }
        return GlobalContext.createStringConstant(key);
    }

    private static void processJsonString(Variable value, JsonItemContext context) {
        context.jsonParserBuilder.getJsonString(value, context.parentJsonObject, getKeyVariable(context.jsonItem.key));
    }

    private static void processJsonInt(Variable value, JsonItemContext context) {
        context.jsonParserBuilder.getJsonInteger(value, context.parentJsonObject, getKeyVariable(context.jsonItem.key));
    }

    private static void processJsonDecimal(Variable value, JsonItemContext context) {
        context.jsonParserBuilder.getJsonDecimal(value, context.parentJsonObject, getKeyVariable(context.jsonItem.key));
    }

    private static void processJsonBoolean(Variable value, JsonItemContext context) {
        context.jsonParserBuilder.getJsonBoolean(value, context.parentJsonObject, getKeyVariable(context.jsonItem.key));
    }

    private static void processJsonStringArray(Variable value, JsonItemContext context) {
        context.jsonParserBuilder.getJsonStringArray(value, context.parentJsonObject, getKeyVariable(context.jsonItem.key));
    }

    private static void processJsonBooleanArray(Variable value, JsonItemContext context) {
        context.jsonParserBuilder.getJsonBooleanArray(value, context.parentJsonObject, getKeyVariable(context.jsonItem.key));
    }

    private static void processJsonDecimalArray(Variable value, JsonItemContext context) {
        context.jsonParserBuilder.getJsonDecimalArray(value, context.parentJsonObject, getKeyVariable(context.jsonItem.key));
    }

    private static void processJsonIntArray(Variable value, JsonItemContext context) {
        context.jsonParserBuilder.getJsonIntArray(value, context.parentJsonObject, getKeyVariable(context.jsonItem.key));
    }
}
