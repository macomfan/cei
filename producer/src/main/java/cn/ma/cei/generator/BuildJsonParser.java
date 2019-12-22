package cn.ma.cei.generator;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.JsonParserBuilder;
import cn.ma.cei.generator.builder.MethodBuilder;
import cn.ma.cei.generator.buildin.JsonWrapper;
import cn.ma.cei.generator.environment.ModelInfo;
import cn.ma.cei.model.json.*;

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

    private static Variable getToVariable(Variable parentModel, String to) {
        if (to == null || to.equals("")) {
            return null;
        }
        String variableName = VariableFactory.isReference(to);
        if (variableName == null) {
            throw new CEIException("[BuildJsonParser] To must be Variable");
        }
        return ModelInfo.queryMember(parentModel.type, variableName);
    }

    private static Variable processJsonItem(JsonItemContext context) {
        if (context.jsonItem == null || context.method == null || context.jsonParserBuilder == null) {
            throw new CEIException("[BuildJsonParser] null");
        }

        if (context.jsonItem instanceof xJsonWithModel) {
            return processJsonWithModel(context);
        } else {
            if (context.parentModel == null || context.parentJsonObject == null) {
                throw new CEIException("[BuildJsonParser] error for normal json item");
            }
            Variable to = getToVariable(context.parentModel, context.jsonItem.to);
            if (to == null) {
                return null;
            }
            if (context.jsonItem instanceof xJsonString) {
                context.jsonParserBuilder.getJsonString(context.parentModel, to, context.parentJsonObject, context.jsonItem.from);
            } else if (context.jsonItem instanceof xJsonInteger) {
                context.jsonParserBuilder.getJsonInteger(context.parentModel, to, context.parentJsonObject, context.jsonItem.from);
            }
            return null;
        }
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
        Variable model = VariableFactory.createLocalVariable(VariableFactory.variableType(jsonWithModel.model), jsonWithModel.model + "_var");
        context.method.registerVariable(model);
        context.jsonParserBuilder.defineModel(model);
        return model;
    }

    private static Variable processJsonObjectArray(JsonItemContext context) {
        xJsonObjectArray jsonWithModel = (xJsonObjectArray) context.jsonItem;
        Variable newJsonObject = defineJsonObject(context);
        context.jsonParserBuilder.getJsonObjectArray(newJsonObject, context.parentJsonObject, jsonWithModel.from);

        Variable eachItemJsonObject = VariableFactory.createLocalVariable(JsonWrapper.getType(), "item");
        context.jsonParserBuilder.startJsonObjectArrayLoop(eachItemJsonObject, newJsonObject);

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

        Variable to = getToVariable(context.parentModel, context.jsonItem.to);
        if (to == null) {
            throw new CEIException("[BuildJsonParser] To for json_object_array is null");
        }
        context.jsonParserBuilder.endJsonObjectArrayLoop(context.parentModel, to, newModel);
        return null;
    }

    private static Variable processJsonObject(JsonItemContext context) {
        xJsonObject jsonWithModel = (xJsonObject) context.jsonItem;
        Variable newJsonObject = defineJsonObject(context);
        context.jsonParserBuilder.getJsonObject(newJsonObject, context.parentJsonObject, jsonWithModel.from);

        Variable newModel = context.parentModel;

        if (jsonWithModel.model != null) {
            newModel = defineModel(context);
        }

        for (xJsonType item : jsonWithModel.itemList) {
            JsonItemContext newContext = new JsonItemContext();
            newContext.jsonItem = item;
            newContext.parentModel = newModel;
            newContext.parentJsonObject = newJsonObject;
            newContext.jsonParserBuilder = context.jsonParserBuilder;
            newContext.method = context.method;
            processJsonItem(newContext);
        }

        Variable to = getToVariable(context.parentModel, context.jsonItem.to);
        if (to == null) {
            return null;
        }

//        if (jsonWithModel.to != null) {
//            String variableName = VariableFactory.isReference(jsonWithModel.to);
//            if (variableName == null) {
//                throw new CEIException("[BuildJsonParser] To must be Variable");
//            }
//            Variable to = Database.getModelInfo().queryMember(context.parentModel.type, variableName);
//            Variable model = VariableFactory.createLocalVariable(VariableFactory.variableType(jsonWithModel.model), jsonWithModel.model + "_var");
//            context.method.registerVariable(model);
//        }
        return null;
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

    private static Variable processJsonWithModel(JsonItemContext context) {
        if (context.parentJsonObject == null && context.responseVariable != null) {
            return processRootJsonObject(context);
        } else if (context.parentJsonObject != null && context.responseVariable == null) {
            if (context.jsonItem instanceof xJsonObject) {
                processJsonObject(context);
            } else if (context.jsonItem instanceof xJsonObjectArray) {
                processJsonObjectArray(context);
            }
        }
        return null;
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
