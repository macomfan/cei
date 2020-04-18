package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.DataProcessorBase;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IJsonBuilderBuilder;
import cn.ma.cei.generator.builder.IJsonCheckerBuilder;
import cn.ma.cei.generator.builder.IJsonParserBuilder;
import cn.ma.cei.generator.buildin.JsonWrapper;
import cn.ma.cei.model.json.*;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.utils.Checker;

public class BuildJsonBuilder extends DataProcessorBase<xJsonBuilder> {
    static class JsonItemContext {
        xJsonType currentItem = null;
        Variable currentJsonObject = null;
        IJsonBuilderBuilder jsonBuilderBuilder = null;
    }


    @Override
    public Variable build(xJsonBuilder jsonBuilder, IDataProcessorBuilder builder) {
        IJsonBuilderBuilder jsonBuilderBuilder = Checker.checkBuilder(builder.createJsonBuilderBuilder(), builder.getClass(), "JsonBuilderBuilder");
        Variable jsonObject;
        if (Checker.isEmpty(jsonBuilder.name)) {
            jsonObject = createTempVariable(JsonWrapper.getType(), "jsonBuilder");
        } else {
            jsonObject = createUserVariable(JsonWrapper.getType(), jsonBuilder.name);
        }

        jsonBuilderBuilder.defineRootJsonObject(jsonObject);
        jsonBuilder.itemList.forEach(item -> item.doBuild(() -> {
            JsonItemContext newContext = new JsonItemContext();
            newContext.currentItem = item;
            newContext.currentJsonObject = jsonObject;
            newContext.jsonBuilderBuilder = jsonBuilderBuilder;
            processJsonItem(newContext);

            Variable from = getFromVariable(item);
            if (from == null) {
                throw new CEIException("[BuildJsonBuilder] cannot process from");
            }
            Variable to = BuilderContext.createStringConstant(item.key);

            if (item instanceof xJsonValue) {
                if (from.getType() == xString.inst.getType()) {
                    jsonBuilderBuilder.addJsonString(from, jsonObject, to);
                } else if (from.getType() == xInt.inst.getType()) {
                    jsonBuilderBuilder.addJsonNumber(from, jsonObject, to);
                }
                // TODO
            } else if (item instanceof xJsonString) {
                jsonBuilderBuilder.addJsonString(from, jsonObject, to);
            } else if (item instanceof xJsonInteger) {
                jsonBuilderBuilder.addJsonNumber(from, jsonObject, to);
            } else if (item instanceof xJsonBoolean) {
                jsonBuilderBuilder.addJsonBoolean(from, jsonObject, to);
            } else if (item instanceof xJsonDecimal) {
                jsonBuilderBuilder.addJsonNumber(from, jsonObject, to);
            }
        }));

        return jsonObject;
    }

    private void processJsonItem(JsonItemContext context) {
        if (context.currentItem.copy != null && (context.currentItem.key != null || context.currentItem.value != null)) {
            CEIErrors.showXMLFailure(context.currentItem, " key, value cannot exist with copy.");
        } else if (context.currentItem.copy != null) {
            context.currentItem.key = context.currentItem.copy;
            context.currentItem.value = "{" + context.currentItem.copy + "}";
            context.currentItem.copy = null;
        }
    }

    private Variable getFromVariable(xJsonType jsonItem) {
        if (jsonItem.key != null && !jsonItem.key.equals("")) {
            return queryVariableOrConstant(jsonItem.value);
        }
        return null;
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
