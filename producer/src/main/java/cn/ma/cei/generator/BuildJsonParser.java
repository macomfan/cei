package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.JsonParserBuilder;
import cn.ma.cei.generator.builder.MethodBuilder;
import cn.ma.cei.generator.buildin.JsonWrapper;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.model.json.*;
import cn.ma.cei.model.types.*;

public class BuildJsonParser {

    public static Variable build(xJsonParser jsonParser,
                                 Variable responseVariable,
                                 VariableType outputModelType,
                                 JsonParserBuilder jsonParserBuilder,
                                 MethodBuilder method) {
        if (jsonParser == null) {
            throw new CEIException("[BuildJsonParser] The root is not json parser");
        }
        Variable rootJsonObject = defineRootJsonObject(method);
        jsonParserBuilder.defineRootJsonObject(rootJsonObject, responseVariable);

        Variable model = method.createLocalVariable(outputModelType, outputModelType.getDescriptor() + "_var");
        jsonParserBuilder.defineModel(model);

        jsonParser.itemList.forEach((item) -> {
            item.startBuilding();
            JsonItemContext newContext = new JsonItemContext();
            newContext.jsonItem = item;
            newContext.parentModel = model;
            newContext.parentJsonObject = rootJsonObject;
            newContext.jsonParserBuilder = jsonParserBuilder;
            newContext.method = method;
            processJsonItem(newContext);
            item.endBuilding();
        });
        return model;
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

    private static Variable processJsonItem(JsonItemContext context) {
        if (context.jsonItem == null || context.method == null || context.jsonParserBuilder == null) {
            throw new CEIException("[BuildJsonParser] null");
        }
        if (context.jsonItem.copy == null && context.jsonItem.from == null && context.jsonItem.to == null) {
            throw new CEIException("[BuildJsonParser] from, to and copy cannot null");
        } else if (context.jsonItem.copy != null && (context.jsonItem.from != null || context.jsonItem.to != null)) {
            throw new CEIException("[BuildJsonParser] from, to cannot exist with copy");
        } else if (context.jsonItem.copy != null && context.jsonItem.from == null && context.jsonItem.to == null) {
            context.jsonItem.from = context.jsonItem.copy;
            context.jsonItem.to = "{" + context.jsonItem.copy + "}";
            context.jsonItem.copy = null;
        }

        if (context.jsonItem instanceof xJsonAuto) {
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
        } else if (context.jsonItem instanceof xJsonArray) {
            Variable to = getToVariable(context.parentModel, context.jsonItem);
            if (to == null) {
                throw new CEIException("[BuildJsonParser] To for json_object_array is null");
            }
            processJsonArray(to, context);
        } else {
            if (context.parentModel == null || context.parentJsonObject == null) {
                throw new CEIException("[BuildJsonParser] error for normal json item");
            }
            Variable to = getToVariable(context.parentModel, context.jsonItem);
            if (to == null) {
                return null;
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
            return null;
        }
        return null;
    }

    private static Variable defineRootJsonObject(MethodBuilder method) {
        Variable jsonObject = method.createLocalVariable(JsonWrapper.getType(), "root_obj");
        return jsonObject;
    }

    private static Variable defineJsonObject(JsonItemContext context) {
        xJsonWithModel jsonWithModel = (xJsonWithModel) context.jsonItem;
        Variable jsonObject = context.method.createLocalVariable(JsonWrapper.getType(), jsonWithModel.from + "_obj");
        return jsonObject;
    }

    private static Variable defineModel(JsonItemContext context) {
        xJsonWithModel jsonWithModel = (xJsonWithModel) context.jsonItem;
        if (jsonWithModel.model == null || "".equals(jsonWithModel)) {
            throw new CEIException("[BuildJsonParser] Model is not defined");
        }
        Variable model = context.method.createLocalVariable(VariableFactory.variableType(jsonWithModel.model), jsonWithModel.model + "_var");
        context.jsonParserBuilder.defineModel(model);
        return model;
    }

    private static void processJsonArray(Variable to, JsonItemContext context) {
        xJsonWithModel jsonWithModel = (xJsonWithModel) context.jsonItem;
        Variable eachItemJsonObject = context.method.createLocalVariable(JsonWrapper.getType(), "item");
        Variable from = VariableFactory.createHardcodeStringVariable(jsonWithModel.from);
        context.jsonParserBuilder.startArrayLoop(eachItemJsonObject, context.parentJsonObject, from);
        Variable newModel = defineModel(context);
        context.jsonParserBuilder.endJsonArrayLoop(to, newModel);

//        Variable eachItemJsonObject = VariableFactory.createLocalVariable(JsonWrapper.getType(), "item");
//        Variable from = VariableFactory.createHardcodeStringVariable(jsonWithModel.from);
//        context.jsonParserBuilder.startJsonObjectArrayLoop(eachItemJsonObject, newJsonObject, from);
//
//        context.jsonParserBuilder.endJsonObjectArrayLoop(newJsonObject, context.parentModel);
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

    private static Variable processJsonObjectArray(Variable to, JsonItemContext context) {
        xJsonWithModel jsonWithModel = (xJsonWithModel) context.jsonItem;
        Variable newJsonObject = context.parentJsonObject;

        Variable eachItemJsonObject = context.method.createLocalVariable(JsonWrapper.getType(), "item");
        Variable from = VariableFactory.createHardcodeStringVariable(jsonWithModel.from);
        context.jsonParserBuilder.startJsonObjectArrayLoop(eachItemJsonObject, newJsonObject, from);

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

        Variable from = VariableFactory.createHardcodeStringVariable(jsonWithModel.from);
        context.jsonParserBuilder.getJsonObject(to, newModel, newJsonObject, context.parentJsonObject, from);

        for (xJsonType item : jsonWithModel.itemList) {
            JsonItemContext newContext = new JsonItemContext();
            newContext.jsonItem = item;
            newContext.parentModel = newModel;
            newContext.parentJsonObject = newJsonObject;
            newContext.jsonParserBuilder = context.jsonParserBuilder;
            newContext.method = context.method;
            processJsonItem(newContext);
        }
    }

    static class JsonItemContext {

        xJsonType jsonItem = null;
        Variable parentModel = null;
        Variable parentJsonObject = null;
        JsonParserBuilder jsonParserBuilder = null;
        MethodBuilder method = null;
    }

}
