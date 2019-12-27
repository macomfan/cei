package cn.ma.cei.generator;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.JsonParserBuilder;
import cn.ma.cei.generator.builder.MethodBuilder;
import cn.ma.cei.generator.buildin.JsonWrapper;
import cn.ma.cei.model.json.*;
import cn.ma.cei.model.types.xBoolean;
import cn.ma.cei.model.types.xDecimal;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.model.types.xLong;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.model.types.xStringArray;

public class BuildJsonParser {

    public static VariableType getModelType(xJsonParser parser) {
        return VariableFactory.variableType(parser.model);
    }

    public static Variable build(xJsonParser jsonParser, Variable responseVariable, JsonParserBuilder jsonParserBuilder, MethodBuilder method) {
        JsonItemContext context = new JsonItemContext();
        context.jsonParserBuilder = jsonParserBuilder;
        context.responseVariable = responseVariable;
        context.jsonItem = jsonParser;
        context.method = method;
        return processJsonItem(context);
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

    private static void processJsonAuto(JsonItemContext context) {
        if (!(context.jsonItem instanceof xJsonAuto)) {
            throw new CEIException("[BuildJsonParser] JsonItem is null");
        }

        Variable to = getToVariable(context.parentModel, context.jsonItem);
        if (to == null) {
            throw new CEIException("[BuildJsonParser] no to or copy defined in json");
        }

        if (to.type.equalTo(xString.typeName)) {
            processJsonString(to, context);
        } else if (to.type.equalTo(xInt.typeName)) {
            processJsonInt(to, context);
        } else if (to.type.equalTo(xLong.typeName)) {
            processJsonLong(to, context);
        } else if (to.type.equalTo(xBoolean.typeName)) {
            processJsonBoolean(to, context);
        } else if (to.type.equalTo(xDecimal.typeName)) {
            processJsonDecimal(to, context);
        } else if (to.type.equalTo(xStringArray.typeName)) {
            processJsonStringArray(to, context);
        } else if (to.type.isObject()) {
            processJsonObject(to, context);
        } else if (to.type.isObjectList()) {
            processJsonObjectArray(to, context);
        }
    }

    private static Variable processJsonItem(JsonItemContext context) {
        if (context.jsonItem == null || context.method == null || context.jsonParserBuilder == null) {
            throw new CEIException("[BuildJsonParser] null");
        }
        if (context.jsonItem.to != null && context.jsonItem.copy != null) {
            throw new CEIException("[BuildJsonParser] to and copy cannot be defined in same item");
        }
        if (context.jsonItem.from != null && context.jsonItem.copy != null) {
            throw new CEIException("[BuildJsonParser] from and copy cannot be defined in same item");
        }
        if (context.jsonItem.from == null) {
            context.jsonItem.from = context.jsonItem.copy;
        }

        if (context.jsonItem instanceof xJsonParser) {
            return processRootJsonObject(context);
        } else if (context.jsonItem instanceof xJsonAuto) {
            processJsonAuto(context);
        } else if (context.jsonItem instanceof xJsonObject) {
            Variable to = getToVariable(context.parentModel, context.jsonItem);
            if (to == null) {
                return null;
            }
            processJsonObject(to, context);
        } else if (context.jsonItem instanceof xJsonObjectArray) {
            Variable to = getToVariable(context.parentModel, context.jsonItem);
            if (to == null) {
                throw new CEIException("[BuildJsonParser] To for json_object_array is null");
            }
            processJsonObjectArray(to, context);
        } else {
            if (context.parentModel == null || context.parentJsonObject == null) {
                throw new CEIException("[BuildJsonParser] error for normal json item");
            }
            Variable to = getToVariable(context.parentModel, context.jsonItem);
            if (to == null) {
                return null;
            }
            if (context.jsonItem instanceof xJsonString) {
                context.jsonParserBuilder.getJsonString(to, context.parentJsonObject, context.jsonItem.from);
            } else if (context.jsonItem instanceof xJsonInteger) {
                context.jsonParserBuilder.getJsonInteger(to, context.parentJsonObject, context.jsonItem.from);
            } else if (context.jsonItem instanceof xJsonLong) {
                context.jsonParserBuilder.getJsonLong(to, context.parentJsonObject, context.jsonItem.from);
            } else if (context.jsonItem instanceof xJsonBoolean) {
                context.jsonParserBuilder.getJsonBoolean(to, context.parentJsonObject, context.jsonItem.from);
            } else if (context.jsonItem instanceof xJsonDecimal) {
                context.jsonParserBuilder.getJsonDecimal(to, context.parentJsonObject, context.jsonItem.from);
            } else if (context.jsonItem instanceof xJsonStringArray) {
                context.jsonParserBuilder.getJsonStringArray(to, context.parentJsonObject, context.jsonItem.from);
            }
            return null;
        }
        return null;
    }

    private static Variable defineRootJsonObject(JsonItemContext context) {
        Variable jsonObject = VariableFactory.createLocalVariable(JsonWrapper.getType(), "root_obj");
        context.method.registerVariable(jsonObject);
        return jsonObject;
    }

    private static Variable defineJsonObject(JsonItemContext context) {
        xJsonWithModel jsonWithModel = (xJsonWithModel) context.jsonItem;
        Variable jsonObject = VariableFactory.createLocalVariable(JsonWrapper.getType(), jsonWithModel.from + "_obj");
        context.method.registerVariable(jsonObject);
        return jsonObject;
    }

    private static Variable defineModel(JsonItemContext context) {
        xJsonWithModel jsonWithModel = (xJsonWithModel) context.jsonItem;
        if (jsonWithModel.model == null || "".equals(jsonWithModel)) {
            throw new CEIException("[BuildJsonParser] Model is not defined");
        }
        Variable model = VariableFactory.createLocalVariable(VariableFactory.variableType(jsonWithModel.model), jsonWithModel.model + "_var");
        context.method.registerVariable(model);
        context.jsonParserBuilder.defineModel(model);
        return model;
    }

    private static void processJsonString(Variable to, JsonItemContext context) {
        context.jsonParserBuilder.getJsonString(to, context.parentJsonObject, context.jsonItem.from);
    }

    private static void processJsonInt(Variable to, JsonItemContext context) {
        context.jsonParserBuilder.getJsonInteger(to, context.parentJsonObject, context.jsonItem.from);
    }

    private static void processJsonLong(Variable to, JsonItemContext context) {
        context.jsonParserBuilder.getJsonLong(to, context.parentJsonObject, context.jsonItem.from);
    }

    private static void processJsonDecimal(Variable to, JsonItemContext context) {
        context.jsonParserBuilder.getJsonDecimal(to, context.parentJsonObject, context.jsonItem.from);
    }

    private static void processJsonBoolean(Variable to, JsonItemContext context) {
        context.jsonParserBuilder.getJsonBoolean(to, context.parentJsonObject, context.jsonItem.from);
    }

    private static void processJsonStringArray(Variable to, JsonItemContext context) {
        context.jsonParserBuilder.getJsonStringArray(to, context.parentJsonObject, context.jsonItem.from);
    }

    private static Variable processJsonObjectArray(Variable to, JsonItemContext context) {
        xJsonWithModel jsonWithModel = (xJsonWithModel) context.jsonItem;
        Variable newJsonObject = context.parentJsonObject;

        Variable eachItemJsonObject = VariableFactory.createLocalVariable(JsonWrapper.getType(), "item");
        context.jsonParserBuilder.startJsonObjectArrayLoop(eachItemJsonObject, newJsonObject, jsonWithModel.from);

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

        context.jsonParserBuilder.endJsonObjectArrayLoop(to, newModel);
        return null;
    }

    private static void processJsonObject(Variable to, JsonItemContext context) {
        if (to == null) {
            return;
        }

        xJsonWithModel jsonWithModel = (xJsonWithModel) context.jsonItem;
        Variable newJsonObject = defineJsonObject(context);

        Variable newModel = context.parentModel;
        if (jsonWithModel.model != null) {
            newModel = defineModel(context);
        }

        context.jsonParserBuilder.getJsonObject(to, newModel, newJsonObject, context.parentJsonObject, jsonWithModel.from);

        for (xJsonType item : jsonWithModel.itemList) {
            JsonItemContext newContext = new JsonItemContext();
            newContext.jsonItem = item;
            newContext.parentModel = newModel;
            newContext.parentJsonObject = newJsonObject;
            newContext.jsonParserBuilder = context.jsonParserBuilder;
            newContext.method = context.method;
            processJsonItem(newContext);
        }
        return;
    }

    private static Variable processRootJsonObject(JsonItemContext context) {
        xJsonParser jsonParser = (xJsonParser) context.jsonItem;
        if (jsonParser == null) {
            throw new CEIException("[BuildJsonParser] The root is not json parser");
        }
        Variable newJsonObject = defineRootJsonObject(context);
        Variable newModel = defineModel(context);
        context.jsonParserBuilder.defineRootJsonObject(newJsonObject, context.responseVariable);

        jsonParser.itemList.forEach((item) -> {
            JsonItemContext newContext = new JsonItemContext();
            newContext.jsonItem = item;
            newContext.parentModel = newModel;
            newContext.parentJsonObject = newJsonObject;
            newContext.jsonParserBuilder = context.jsonParserBuilder;
            newContext.method = context.method;
            processJsonItem(newContext);
        });
        return newModel;
    }

    static class JsonItemContext {

        xJsonType jsonItem = null;
        Variable parentModel = null;
        Variable parentJsonObject = null;
        Variable responseVariable = null;
        JsonParserBuilder jsonParserBuilder = null;
        MethodBuilder method = null;
    }

}
