package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.DataProcessorBase;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IJsonBuilderBuilder;
import cn.ma.cei.generator.buildin.JsonWrapper;
import cn.ma.cei.model.json.*;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.model.types.xString;

public class BuildJsonBuilder extends DataProcessorBase<xJsonBuilder> {
    @Override
    public Variable build(xJsonBuilder jsonBuilder, IDataProcessorBuilder builder) {
        IJsonBuilderBuilder jsonBuilderBuilder = builder.createJsonBuilderBuilder();
        if (jsonBuilderBuilder == null) {
            throw new CEIException("JsonBuilderBuilder is null");
        }
        Variable jsonObject = createTempVariable(JsonWrapper.getType(), "jsonBuilder");
        jsonBuilderBuilder.defineRootJsonObject(jsonObject);
        jsonBuilder.itemList.forEach(item -> item.doBuild(() -> {
            if (item.copy == null) {
                if (item.key == null || item.value == null) {
                    throw new CEIException("[BuildJsonBuilder] from, to and copy cannot be null");
                }
            } else {
                if (item.key != null && item.value != null) {
                    throw new CEIException("[BuildJsonBuilder] from, to cannot exist with copy");
                } else {
                    item.key = item.copy;
                    item.value = "{" + item.copy + "}";
                    item.copy = null;
                }
            }
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
