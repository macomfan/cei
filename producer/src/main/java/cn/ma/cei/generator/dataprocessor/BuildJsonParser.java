package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.*;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IJsonCheckerBuilder;
import cn.ma.cei.generator.builder.IJsonParserBuilder;
import cn.ma.cei.generator.buildin.JsonChecker;
import cn.ma.cei.generator.buildin.JsonWrapper;
import cn.ma.cei.model.json.*;
import cn.ma.cei.model.types.*;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.RegexHelper;

public class BuildJsonParser extends DataProcessorBase<xJsonParser> {

    static class JsonCheckContext {
        Variable jsonCheckerObject;
        IJsonCheckerBuilder jsonCheckerBuilder;
    }

    static class JsonItemContext {
        xJsonType jsonItem = null;
        Variable parentModel = null;
        Variable parentJsonObject = null;
        IJsonParserBuilder jsonParserBuilder = null;
    }

    @Override
    public Variable build(xJsonParser jsonParser, IDataProcessorBuilder builder) {
        Checker.isNull(builder, BuildJsonParser.class, "IDataProcessorBuilder");
        IJsonParserBuilder jsonParserBuilder = builder.createJsonParserBuilder();
        Checker.isNull(jsonParserBuilder, BuildJsonParser.class, "IJsonParserBuilder");
        if (jsonParser == null) {
            throw new CEIException("[BuildJsonParser] The root is not json parser");
        }
        Variable inputVariable = getInputVariable(jsonParser);
        Variable rootJsonObject = defineRootJsonObject();
        jsonParserBuilder.defineRootJsonObject(rootJsonObject, inputVariable);

        if ((jsonParser.itemList == null || jsonParser.itemList.isEmpty()) && jsonParser.jsonChecker == null) {
            throw new CEIException("[BuildJsonParser] No any item in json parser");
        }

        if (jsonParser.jsonChecker != null) {
            jsonParser.jsonChecker.doBuild(() -> {
                buildJsonChecker(jsonParser.jsonChecker, jsonParserBuilder, rootJsonObject);
            });
        }

        if (jsonParser.itemList == null || jsonParser.itemList.isEmpty() || jsonParser.model == null) {
            return null;
        }
        VariableType outputModelType = BuilderContext.variableType(jsonParser.model);
        Variable model = createTempVariable(outputModelType, outputModelType.getDescriptor() + "Var");
        jsonParserBuilder.defineModel(model);

        jsonParser.itemList.forEach(item -> item.doBuild(() -> {
            JsonItemContext newContext = new JsonItemContext();
            newContext.jsonItem = item;
            newContext.parentModel = model;
            newContext.parentJsonObject = rootJsonObject;
            newContext.jsonParserBuilder = jsonParserBuilder;
            processJsonItem(newContext);
        }));
        return model;
    }

    @Override
    public VariableType returnType(xJsonParser item) {
        return BuilderContext.variableType(item.model);
    }

    @Override
    public String resultVariableName(xJsonParser item) {
        return null;
    }


    private Variable getInputVariable(xJsonParser jsonParser) {
        if (Checker.isEmpty(jsonParser.input)) {
            return getDefaultInput();
        } else {
            return queryVariable(jsonParser.input);
        }
    }

    private Variable defineRootJsonObject() {
        return createTempVariable(JsonWrapper.getType(), "rootObj");
    }

    private void buildJsonChecker(
            xJsonChecker jsonChecker,
            IJsonParserBuilder jsonParserBuilder,
            Variable rootJsonObject) {
        IJsonCheckerBuilder jsonCheckerBuilder = jsonParserBuilder.createJsonCheckerBuilder();
        if (jsonCheckerBuilder == null) {
            throw new CEIException("[BuildJsonParser] JsonChecker build is null");
        }
        //BuildJsonChecker.build(jsonChecker, rootJsonObject, jsonCheckerBuilder);

        Variable jsonCheckerVar = createTempVariable(JsonChecker.getType(), "jsonChecker");
        jsonCheckerBuilder.defineJsonChecker(jsonCheckerVar, rootJsonObject);
        jsonChecker.items.forEach(item -> {
            JsonCheckContext context = new JsonCheckContext();
            context.jsonCheckerObject = jsonCheckerVar;
            context.jsonCheckerBuilder = jsonCheckerBuilder;

            if (item instanceof xJsonEqual) {
                ProcessEqual((xJsonEqual) item, jsonCheckerBuilder);
            } else if (item instanceof xJsonNotEqual) {
                ProcessNotEqual((xJsonNotEqual) item);
            }
        });
        if (jsonChecker.usedFor == IJsonCheckerBuilder.UsedFor.REPORT_ERROR) {
            jsonCheckerBuilder.reportError(jsonCheckerVar);
        } else if (jsonChecker.usedFor == IJsonCheckerBuilder.UsedFor.RETURN_RESULT) {
            jsonCheckerBuilder.returnResult(jsonCheckerVar);
        } else {
            // TODO
            return;
        }


    }

    private void  processJsonItem(JsonItemContext context) {
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
    }

    private void processJsonValue(Variable value, JsonItemContext context) {
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

    private void processJsonObject(Variable value, JsonItemContext context) {
        xJsonWithModel jsonWithModel = (xJsonWithModel) context.jsonItem;
        Variable key = getKeyVariable(jsonWithModel.key);
        Variable newJsonObject = defineJsonObject(context);
        Variable newModel = defineModel(context);
        context.jsonParserBuilder.defineJsonObject(newJsonObject, context.parentJsonObject, key);

        jsonWithModel.itemList.forEach(item -> item.doBuild(() -> {
            JsonItemContext newContext = new JsonItemContext();
            newContext.jsonItem = item;
            newContext.parentModel = newModel;
            newContext.parentJsonObject = newJsonObject;
            newContext.jsonParserBuilder = context.jsonParserBuilder;
            processJsonItem(newContext);
        }));
        if (value != null) {
            context.jsonParserBuilder.assignModel(value, newModel);
        }
    }

    private void processJsonObjectArray(Variable to, JsonItemContext context) {
        xJsonWithModel jsonWithModel = (xJsonWithModel) context.jsonItem;
        Variable newJsonObject = defineJsonObject(context);
        if (!Checker.isEmpty(jsonWithModel.key)) {
            Variable key = getKeyVariable(jsonWithModel.key);
            context.jsonParserBuilder.defineJsonObject(newJsonObject, context.parentJsonObject, key);
        }
        Variable eachItemJsonObject = createTempVariable(JsonWrapper.getType(), "item");
        context.jsonParserBuilder.startJsonObjectArray(eachItemJsonObject, newJsonObject);
        Variable newModel = defineModel(context);

        jsonWithModel.itemList.forEach((item) -> item.doBuild(() ->{
            JsonItemContext newContext = new JsonItemContext();
            newContext.jsonItem = item;
            newContext.parentModel = newModel;
            newContext.parentJsonObject = eachItemJsonObject;
            newContext.jsonParserBuilder = context.jsonParserBuilder;
            processJsonItem(newContext);
        }));
        context.jsonParserBuilder.endJsonObjectArray(to, newModel);
    }

    private Variable defineJsonObject(JsonItemContext context) {
        xJsonWithModel jsonWithModel = (xJsonWithModel) context.jsonItem;
        if (Checker.isEmpty(jsonWithModel.key)) {
            // If key is null, use the parent json object
            return context.parentJsonObject;
        }
        return createTempVariable(JsonWrapper.getType(), jsonWithModel.key + "Obj");
    }

    private Variable defineModel(JsonItemContext context) {
        xJsonWithModel jsonWithModel = (xJsonWithModel) context.jsonItem;
        if (Checker.isEmpty(jsonWithModel.model)) {
            // If model is null, use the parent model
            return context.parentModel;
        }
        Variable model = createTempVariable(BuilderContext.variableType(jsonWithModel.model), jsonWithModel.model + "Var");
        context.jsonParserBuilder.defineModel(model);
        return model;
    }

    private Variable getValueVariable(Variable parentModel, xJsonType jsonItem) {
        if (!Checker.isEmpty(jsonItem.value)) {
            String variableName = RegexHelper.isReference(jsonItem.value);
            if (variableName == null) {
                throw new CEIException("[BuildJsonParser] To must be Variable");
            }
            return parentModel.queryMember(variableName);
        } else {
            return null;
        }
    }

    private Variable getKeyVariable(String key) {
        if (Checker.isEmpty(key)) {
            throw new CEIException("[BuildJsonParser] Key cannot be null");
        }
        return BuilderContext.createStringConstant(key);
    }

    private void processJsonString(Variable value, JsonItemContext context) {
        context.jsonParserBuilder.getJsonString(value, context.parentJsonObject, getKeyVariable(context.jsonItem.key));
    }

    private void processJsonInt(Variable value, JsonItemContext context) {
        context.jsonParserBuilder.getJsonInteger(value, context.parentJsonObject, getKeyVariable(context.jsonItem.key));
    }

    private void processJsonDecimal(Variable value, JsonItemContext context) {
        context.jsonParserBuilder.getJsonDecimal(value, context.parentJsonObject, getKeyVariable(context.jsonItem.key));
    }

    private void processJsonBoolean(Variable value, JsonItemContext context) {
        context.jsonParserBuilder.getJsonBoolean(value, context.parentJsonObject, getKeyVariable(context.jsonItem.key));
    }

    private void processJsonStringArray(Variable value, JsonItemContext context) {
        context.jsonParserBuilder.getJsonStringArray(value, context.parentJsonObject, getKeyVariable(context.jsonItem.key));
    }

    private void processJsonBooleanArray(Variable value, JsonItemContext context) {
        context.jsonParserBuilder.getJsonBooleanArray(value, context.parentJsonObject, getKeyVariable(context.jsonItem.key));
    }

    private void processJsonDecimalArray(Variable value, JsonItemContext context) {
        context.jsonParserBuilder.getJsonDecimalArray(value, context.parentJsonObject, getKeyVariable(context.jsonItem.key));
    }

    private void processJsonIntArray(Variable value, JsonItemContext context) {
        context.jsonParserBuilder.getJsonIntArray(value, context.parentJsonObject, getKeyVariable(context.jsonItem.key));
    }

    private void ProcessEqual(xJsonEqual jsonEqual, IJsonCheckerBuilder builder) {
//        Variable key = VariableFactory.createHardcodeStringVariable(jsonEqual.key);
//        builder.setEqual();
    }

    private void ProcessNotEqual(xJsonNotEqual jsonNotEqual) {

    }
}
