package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.DataProcessorBase;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
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

    static class JsonItemContext {
        xJsonType currentItem = null;
        Variable currentModel = null;
        Variable currentJsonObject = null;
        IJsonParserBuilder jsonParserBuilder = null;
        // For json checker
        Variable jsonCheckerObject;
        IJsonCheckerBuilder jsonCheckerBuilder = null;
    }

    @Override
    public Variable build(xJsonParser jsonParser, IDataProcessorBuilder builder) {
        IJsonParserBuilder jsonParserBuilder =
                Checker.checkNull(builder.createJsonParserBuilder(), builder, "JsonParserBuilder");
        Variable inputVariable = queryInputVariable(jsonParser.input, null, xString.inst.getType());

        // Define the root json object.
        builder.onAddReference(JsonWrapper.getType());
        Variable rootJsonObject = defineRootJsonObject(jsonParser);
        jsonParserBuilder.defineRootJsonObject(rootJsonObject, inputVariable);
        if ((jsonParser.itemList == null || jsonParser.itemList.isEmpty()) && jsonParser.jsonChecker == null) {
            CEIErrors.showXMLFailure("json_parser do not have any items");
        }

        if (jsonParser.jsonChecker != null) {
            jsonParser.jsonChecker.doBuild(() -> buildJsonChecker(jsonParser.jsonChecker, jsonParserBuilder, rootJsonObject));
        }

        if (jsonParser.itemList == null || jsonParser.itemList.isEmpty() || jsonParser.model == null) {
            // Only json checked defined
            return null;
        }
        VariableType outputModelType = BuilderContext.variableType(jsonParser.model);
        Variable model = createTempVariable(outputModelType, outputModelType.getDescriptor() + "Var");
        jsonParserBuilder.defineModel(model);

        jsonParser.itemList.forEach(item -> item.doBuild(() -> {
            JsonItemContext newContext = new JsonItemContext();
            newContext.currentItem = item;
            newContext.currentModel = model;
            newContext.currentJsonObject = rootJsonObject;
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

    private Variable defineRootJsonObject(xJsonParser jsonParser) {
        if (Checker.isEmpty(jsonParser.name)) {
            return createTempVariable(JsonWrapper.getType(), "rootObj");
        } else {
            return createUserVariable(JsonWrapper.getType(), jsonParser.name);
        }
    }

    private void buildJsonChecker(
            xJsonChecker jsonChecker,
            IJsonParserBuilder jsonParserBuilder,
            Variable rootJsonObject) {
        IJsonCheckerBuilder jsonCheckerBuilder =
                Checker.checkNull(jsonParserBuilder.createJsonCheckerBuilder(), jsonParserBuilder, "JsonCheckerBuilder");

        Variable jsonCheckerVar = createTempVariable(JsonChecker.getType(), "jsonChecker");
        jsonCheckerBuilder.defineJsonChecker(jsonCheckerVar);

        jsonChecker.itemList.forEach(item -> item.doBuild(() -> {
            JsonItemContext context = new JsonItemContext();
            context.currentItem = item;
            context.currentJsonObject = rootJsonObject;
            context.jsonCheckerObject = jsonCheckerVar;
            context.jsonCheckerBuilder = jsonCheckerBuilder;
            context.jsonParserBuilder = jsonParserBuilder;
            processJsonItem(context);
        }));


        if (jsonChecker.usedFor == IJsonCheckerBuilder.UsedFor.REPORT_ERROR) {
            jsonCheckerBuilder.reportError(jsonCheckerVar);
        } else if (jsonChecker.usedFor == IJsonCheckerBuilder.UsedFor.RETURN_RESULT) {
            jsonCheckerBuilder.returnResult(jsonCheckerVar);
        } else {
            // TODO
            return;
        }
    }

    private boolean processJsonCheckerItem(JsonItemContext context) {
        // Process Json checker item firstly
        if (context.currentItem instanceof xJsonCheckerEqual) {
            processCheckerEqual(context);
            return true;
        } else if (context.currentItem instanceof xJsonCheckerNotEqual) {
            processCheckerNotEqual(context);
            return true;
        } else if (context.currentItem instanceof xJsonCheckerContainKey) {
            processCheckerContainKey(context);
            return true;
        }
        return false;
    }

    private void processJsonItem(JsonItemContext context) {
        if (processJsonCheckerItem(context)) {
            // if it is json checker item, do not continue.
            return;
        }

        Variable value = getValueVariable(context.currentModel, context.currentItem);

        if (context.currentItem instanceof xJsonValue) {
            processJsonValue(value, context);
        } else if (context.currentItem instanceof xJsonObject) {
            processJsonObject(value, context);
        } else if (context.currentItem instanceof xJsonObjectForEach) {
            processJsonObjectForEach(value, context);
        } else {
            if (context.currentModel == null || context.currentJsonObject == null) {
                throw new CEIException("[BuildJsonParser] error for normal json item");
            }
            if (value == null) {
                throw new CEIException("[BuildJsonParser] value cannot be null for basic type");
            }
            if (context.currentItem instanceof xJsonString) {
                assignJsonString(value, context);
            } else if (context.currentItem instanceof xJsonInteger) {
                assignJsonInt(value, context);
            } else if (context.currentItem instanceof xJsonBoolean) {
                assignJsonBoolean(value, context);
            } else if (context.currentItem instanceof xJsonDecimal) {
                assignJsonDecimal(value, context);
            } else if (context.currentItem instanceof xJsonStringArray) {
                assignJsonStringArray(value, context);
            } else if (context.currentItem instanceof xJsonDecimalArray) {
                assignJsonDecimalArray(value, context);
            } else if (context.currentItem instanceof xJsonIntArray) {
                assignJsonIntArray(value, context);
            } else if (context.currentItem instanceof xJsonBooleanArray) {
                assignJsonBooleanArray(value, context);
            }
        }
    }

    private void processJsonValue(Variable value, JsonItemContext context) {
        if (value == null) {
            throw new CEIException("[BuildJsonParser] value cannot be null for basic type");
        }
        if (value.getType().equalTo(xString.typeName)) {
            assignJsonString(value, context);
        } else if (value.getType().equalTo(xInt.typeName)) {
            assignJsonInt(value, context);
        } else if (value.getType().equalTo(xBoolean.typeName)) {
            assignJsonBoolean(value, context);
        } else if (value.getType().equalTo(xDecimal.typeName)) {
            assignJsonDecimal(value, context);
        } else if (value.getType().equalTo(xStringArray.typeName)) {
            assignJsonStringArray(value, context);
        } else if (value.getType().equalTo(xDecimalArray.typeName)) {
            assignJsonDecimalArray(value, context);
        } else if (value.getType().equalTo(xBooleanArray.typeName)) {
            assignJsonBooleanArray(value, context);
        } else if (value.getType().equalTo(xIntArray.typeName)) {
            assignJsonIntArray(value, context);
        } else {
            throw new CEIException("[BuildJsonParser] json_value only support the basic type");
        }
    }

    private void processJsonObject(Variable value, JsonItemContext context) {
        xJsonWithModel jsonWithModel = (xJsonWithModel) context.currentItem;
        Variable key = getKeyVariable(jsonWithModel.key);
        Variable newJsonObject = defineJsonObject(context);
        Variable newModel = defineModel(context);
        context.jsonParserBuilder.getJsonObject(newJsonObject, context.currentJsonObject, key, jsonWithModel.optional);

        jsonWithModel.itemList.forEach(item -> item.doBuild(() -> {
            JsonItemContext newContext = new JsonItemContext();
            newContext.currentItem = item;
            newContext.currentModel = newModel;
            newContext.currentJsonObject = newJsonObject;
            newContext.jsonParserBuilder = context.jsonParserBuilder;
            newContext.jsonCheckerObject = context.jsonCheckerObject;
            newContext.jsonCheckerBuilder = context.jsonCheckerBuilder;
            processJsonItem(newContext);
        }));
        if (value != null) {
            context.jsonParserBuilder.assignModel(value, newModel);
        }
    }

    private void processJsonObjectForEach(Variable value, JsonItemContext context) {
        xJsonWithModel jsonWithModel = (xJsonWithModel) context.currentItem;
        Variable newJsonObject = defineJsonObject(context);
        if (!Checker.isEmpty(jsonWithModel.key)) {
            Variable key = getKeyVariable(jsonWithModel.key);
            context.jsonParserBuilder.getJsonArray(newJsonObject, context.currentJsonObject, key, jsonWithModel.optional);
        }
        Variable eachItemJsonObject = createTempVariable(JsonWrapper.getType(), "item");
        context.jsonParserBuilder.startJsonObjectArray(eachItemJsonObject, newJsonObject);
        Variable newModel = defineModel(context);

        jsonWithModel.itemList.forEach((item) -> item.doBuild(() -> {
            JsonItemContext newContext = new JsonItemContext();
            newContext.currentItem = item;
            newContext.currentModel = newModel;
            newContext.currentJsonObject = eachItemJsonObject;
            newContext.jsonParserBuilder = context.jsonParserBuilder;
            newContext.jsonCheckerObject = context.jsonCheckerObject;
            newContext.jsonCheckerBuilder = context.jsonCheckerBuilder;
            processJsonItem(newContext);
        }));
        context.jsonParserBuilder.endJsonObjectArray(value, newModel);
    }

    private Variable defineJsonObject(JsonItemContext context) {
        xJsonWithModel jsonWithModel = (xJsonWithModel) context.currentItem;
        if (Checker.isEmpty(jsonWithModel.key)) {
            // If key is null, use the parent json object
            return context.currentJsonObject;
        }
        return createTempVariable(JsonWrapper.getType(), "obj");
    }

    private Variable defineModel(JsonItemContext context) {
        xJsonWithModel jsonWithModel = (xJsonWithModel) context.currentItem;
        if (Checker.isEmpty(jsonWithModel.model)) {
            // If model is null, use the parent model
            return context.currentModel;
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
            return parentModel.getMember(variableName);
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

    private void assignJsonString(Variable value, JsonItemContext context) {
        context.jsonParserBuilder.getJsonString(value, context.currentJsonObject, getKeyVariable(context.currentItem.key), context.currentItem.optional);
    }

    private void assignJsonInt(Variable value, JsonItemContext context) {
        context.jsonParserBuilder.getJsonInteger(value, context.currentJsonObject, getKeyVariable(context.currentItem.key), context.currentItem.optional);
    }

    private void assignJsonDecimal(Variable value, JsonItemContext context) {
        context.jsonParserBuilder.getJsonDecimal(value, context.currentJsonObject, getKeyVariable(context.currentItem.key), context.currentItem.optional);
    }

    private void assignJsonBoolean(Variable value, JsonItemContext context) {
        context.jsonParserBuilder.getJsonBoolean(value, context.currentJsonObject, getKeyVariable(context.currentItem.key), context.currentItem.optional);
    }

    private void assignJsonStringArray(Variable value, JsonItemContext context) {
        context.jsonParserBuilder.assignJsonStringArray(value, context.currentJsonObject, getKeyVariable(context.currentItem.key), context.currentItem.optional);
    }

    private void assignJsonBooleanArray(Variable value, JsonItemContext context) {
        context.jsonParserBuilder.assignJsonBooleanArray(value, context.currentJsonObject, getKeyVariable(context.currentItem.key), context.currentItem.optional);
    }

    private void assignJsonDecimalArray(Variable value, JsonItemContext context) {
        context.jsonParserBuilder.assignJsonDecimalArray(value, context.currentJsonObject, getKeyVariable(context.currentItem.key), context.currentItem.optional);
    }

    private void assignJsonIntArray(Variable value, JsonItemContext context) {
        context.jsonParserBuilder.assignJsonIntArray(value, context.currentJsonObject, getKeyVariable(context.currentItem.key), context.currentItem.optional);
    }

    private void processCheckerEqual(JsonItemContext context) {
        xJsonCheckerEqual jsonCheckerEqual = (xJsonCheckerEqual) context.currentItem;
        Variable key = queryVariableOrConstant(jsonCheckerEqual.key, xString.inst.getType());
        Variable value = queryVariableOrConstant(jsonCheckerEqual.value, xString.inst.getType());
        context.jsonCheckerBuilder.setEqual(context.jsonCheckerObject, key, value, context.currentJsonObject);
    }

    private void processCheckerNotEqual(JsonItemContext context) {
        xJsonCheckerNotEqual jsonCheckerNotEqual = (xJsonCheckerNotEqual) context.currentItem;
        Variable key = queryVariableOrConstant(jsonCheckerNotEqual.key, xString.inst.getType());
        Variable value = queryVariableOrConstant(jsonCheckerNotEqual.value, xString.inst.getType());
        context.jsonCheckerBuilder.setNotEqual(context.jsonCheckerObject, key, value, context.currentJsonObject);
    }

    private void processCheckerContainKey(JsonItemContext context) {
        xJsonCheckerContainKey jsonCheckerContainKey = (xJsonCheckerContainKey) context.currentItem;
        Variable key = queryVariableOrConstant(jsonCheckerContainKey.key, xString.inst.getType());
        context.jsonCheckerBuilder.setContainKey(context.jsonCheckerObject, key, context.currentJsonObject);
    }
}
