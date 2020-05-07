package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.DataProcessorBase;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IJsonBuilderBuilder;
import cn.ma.cei.generator.buildin.JsonWrapper;
import cn.ma.cei.model.json.*;
import cn.ma.cei.model.types.*;
import cn.ma.cei.utils.Checker;

public class BuildJsonBuilder extends DataProcessorBase<xJsonBuilder> {
    static class JsonItemContext {
        xJsonType currentItem;
        Variable currentJsonObject;
        IJsonBuilderBuilder jsonBuilderBuilder;
        IDataProcessorBuilder dataProcessorBuilder;

        public JsonItemContext(xJsonType currentItem, Variable currentJsonObject, IJsonBuilderBuilder jsonBuilderBuilder, IDataProcessorBuilder dataProcessorBuilder) {
            this.currentItem = currentItem;
            this.currentJsonObject = currentJsonObject;
            this.jsonBuilderBuilder = jsonBuilderBuilder;
            this.dataProcessorBuilder = dataProcessorBuilder;
        }
    }

    @Override
    public Variable build(xJsonBuilder jsonBuilder, IDataProcessorBuilder builder) {
        IJsonBuilderBuilder jsonBuilderBuilder = Checker.checkNull(builder.createJsonBuilderBuilder(), builder, "JsonBuilderBuilder");
        Variable jsonObject;
        if (Checker.isEmpty(jsonBuilder.name)) {
            jsonObject = createTempVariable(JsonWrapper.getType(), "jsonBuilder");
        } else {
            jsonObject = createUserVariable(JsonWrapper.getType(), jsonBuilder.name);
        }

        jsonBuilderBuilder.defineJsonObject(jsonObject);
        jsonBuilder.itemList.forEach(item -> item.doBuild(() -> {
            JsonItemContext newContext = new JsonItemContext(
                    item,
                    jsonObject,
                    jsonBuilderBuilder,
                    builder);
            processJsonItem(newContext);
        }));

        return jsonObject;
    }

    private void processJsonItem(JsonItemContext context) {
        Variable value = getValueVariable(context.currentItem);

        if (context.currentItem instanceof xJsonValue) {
            processJsonValue(value, context);
        } else if (context.currentItem instanceof xJsonObject) {
            processJsonObject(value, context);
        } else if (context.currentItem instanceof xJsonObjectForEach) {
            processJsonObjectForEach(value, context);
        } else {
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
        context.jsonBuilderBuilder.defineJsonObject(newJsonObject);

        jsonWithModel.itemList.forEach(item -> item.doBuild(() -> {
            JsonItemContext newContext = new JsonItemContext(
                    item,
                    newJsonObject,
                    context.jsonBuilderBuilder,
                    context.dataProcessorBuilder);
            processJsonItem(newContext);
        }));

        context.jsonBuilderBuilder.addJsonObject(newJsonObject, context.currentJsonObject, key);
    }

    private void processJsonObjectForEach(Variable to, JsonItemContext context) {
        xJsonWithModel jsonWithModel = (xJsonWithModel) context.currentItem;
        Variable newJsonObject = defineJsonObject(context);
        if (!Checker.isEmpty(jsonWithModel.key)) {
            Variable key = getKeyVariable(jsonWithModel.key);
            context.jsonBuilderBuilder.defineJsonObject(newJsonObject);
        }
//        Variable eachItemJsonObject = createTempVariable(JsonWrapper.getType(), "item");
//        context.jsonBuilderBuilder.startJsonObjectArray(eachItemJsonObject, newJsonObject);
//        Variable newModel = defineModel(context);
//
//        jsonWithModel.itemList.forEach((item) -> item.doBuild(() -> {
//            BuildJsonParser.JsonItemContext newContext = new BuildJsonParser.JsonItemContext();
//            newContext.currentItem = item;
//            newContext.parentModel = newModel;
//            newContext.parentJsonObject = eachItemJsonObject;
//            newContext.jsonParserBuilder = context.jsonParserBuilder;
//            processJsonItem(newContext);
//        }));
//        context.jsonParserBuilder.endJsonObjectArray(to, newModel);
    }

    private void assignJsonString(Variable value, JsonItemContext context) {
        Variable result = TypeConverter.convertType(value, xString.inst.getType(), context.dataProcessorBuilder);
        context.jsonBuilderBuilder.addJsonString(result, context.currentJsonObject, getKeyVariable(context.currentItem.key));
    }

    private void assignJsonInt(Variable value, JsonItemContext context) {
        Variable result = TypeConverter.convertType(value, xInt.inst.getType(), context.dataProcessorBuilder);
        context.jsonBuilderBuilder.addJsonInt(result, context.currentJsonObject, getKeyVariable(context.currentItem.key));
    }

    private void assignJsonDecimal(Variable value, JsonItemContext context) {
        Variable result = TypeConverter.convertType(value, xDecimal.inst.getType(), context.dataProcessorBuilder);
        context.jsonBuilderBuilder.addJsonDecimal(result, context.currentJsonObject, getKeyVariable(context.currentItem.key));
    }

    private void assignJsonBoolean(Variable value, JsonItemContext context) {
        Variable result = TypeConverter.convertType(value, xBoolean.inst.getType(), context.dataProcessorBuilder);
        context.jsonBuilderBuilder.addJsonBoolean(result, context.currentJsonObject, getKeyVariable(context.currentItem.key));
    }

    private void assignJsonStringArray(Variable value, JsonItemContext context) {
        context.jsonBuilderBuilder.addJsonStringArray(value, context.currentJsonObject, getKeyVariable(context.currentItem.key));
    }

    private void assignJsonBooleanArray(Variable value, JsonItemContext context) {
        context.jsonBuilderBuilder.addJsonBooleanArray(value, context.currentJsonObject, getKeyVariable(context.currentItem.key));
    }

    private void assignJsonDecimalArray(Variable value, JsonItemContext context) {
        context.jsonBuilderBuilder.addJsonDecimalArray(value, context.currentJsonObject, getKeyVariable(context.currentItem.key));
    }

    private void assignJsonIntArray(Variable value, JsonItemContext context) {
        context.jsonBuilderBuilder.addJsonIntegerArray(value, context.currentJsonObject, getKeyVariable(context.currentItem.key));
    }

    private Variable getKeyVariable(String key) {
        if (Checker.isEmpty(key)) {
            CEIErrors.showCodeFailure(this.getClass(), "Key cannot be null");
        }
        return BuilderContext.createStringConstant(key);
    }

    private Variable getValueVariable(xJsonType jsonItem) {
        if (Checker.isEmpty(jsonItem.value)) {
            return null;
        }
        if ("{ts.timestamp}".equals(jsonItem.value)) {
            int a = 0;
        }
        Variable value = queryVariableOrConstant(jsonItem.value);
        if (value == null) {
            CEIErrors.showXMLFailure("Cannot define the value: %s", jsonItem.value);
        }
        return value;
    }

    private Variable defineJsonObject(JsonItemContext context) {
        xJsonWithModel jsonWithModel = (xJsonWithModel) context.currentItem;
        if (Checker.isEmpty(jsonWithModel.key)) {
            // If key is null, use the parent json object
            return context.currentJsonObject;
        }
        return createTempVariable(JsonWrapper.getType(), "obj");
    }

    @Override
    public VariableType returnType(xJsonBuilder item) {
        return xString.inst.getType();
    }

    @Override
    public String resultVariableName(xJsonBuilder item) {
        return item.name;
    }
}
