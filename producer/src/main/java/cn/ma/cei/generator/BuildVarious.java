package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.JsonBuilderBuilder;
import cn.ma.cei.generator.builder.MethodBuilder;
import cn.ma.cei.generator.builder.StringBuilderBuilder;
import cn.ma.cei.generator.buildin.JsonWrapper;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.model.base.xVarious;
import cn.ma.cei.model.json.*;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.model.xAttribute;
import cn.ma.cei.utils.ReflectionHelper;
import cn.ma.cei.utils.RegexHelper;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class BuildVarious {
    public static Variable createValueFromAttribute(String attrName, xVarious various, MethodBuilder methodBuilder) {
        Field field = ReflectionHelper.getFieldByName(various, attrName);
        if (field == null) {
            throw new CEIException("Cannot find attribute " + attrName);
        }
        String attrValue = null;
        try {
            attrValue = (String)field.get(various);
        } catch (IllegalArgumentException | IllegalAccessException ignored) {

        }
        if (attrValue == null) {
            // not define
            xAttribute valueProcessor = various.getAttributeByName(attrName);
            if (valueProcessor == null) {
                throw new CEIException("Attribute " + attrName + " do not have both value and value processor");
            }
            if (valueProcessor.jsonBuilder != null) {
                JsonBuilderBuilder jsonBuilderBuilder = methodBuilder.createJsonBuilderBuilder();
                if (jsonBuilderBuilder == null) {
                    throw new CEIException("JsonBuilderBuilder is null");
                }
                return buildJsonBuilder(valueProcessor.jsonBuilder, jsonBuilderBuilder, methodBuilder);
            } else if (valueProcessor.stringBuilder != null) {
                return buildStringBuilder(valueProcessor, null);
            } else {
                return null;
            }

        } else if (RegexHelper.isReference(attrValue) != null) {
                Variable var = methodBuilder.queryVariable(RegexHelper.isReference(attrValue));
                if (var == null) {
                    throw new CEIException("Cannot lookup variable: " + var);
                }
                return var;
        } else {
            List<String> linkedParam = RegexHelper.findReference(attrValue);
            if (linkedParam.isEmpty()) {
                return VariableFactory.createHardcodeStringVariable(attrValue);
            } else {
                StringBuilderBuilder stringBuilderBuilder = methodBuilder.createStringBuilderBuilder();
                if (stringBuilderBuilder == null) {
                    throw new CEIException("StringBuilderBuilder is null");
                }
                List<Variable> variables = new LinkedList<>();
                variables.add(VariableFactory.createHardcodeStringVariable(attrValue));
                linkedParam.forEach(item -> {
                    Variable param = methodBuilder.queryVariable(item);
                    if (param == null) {
                        throw new CEIException("Cannot find variable in target");
                    }
                    variables.add(param);
                });
                Variable[] params = new Variable[variables.size()];
                variables.toArray(params);
                return stringBuilderBuilder.stringReplacement(params);
            }
        }
    }

    private static Variable buildJsonBuilder(xJsonBuilder jsonBuilder, JsonBuilderBuilder jsonBuilderBuilder, MethodBuilder methodBuilder) {
        Variable jsonObject = methodBuilder.newLocalVariable(JsonWrapper.getType(), "{jsonBuilder}");
        jsonBuilderBuilder.defineRootJsonObject(jsonObject);
        jsonBuilder.itemList.forEach(item -> {
            item.startBuilding();
            if (item.copy == null) {
                if (item.from == null || item.to == null) {
                    throw new CEIException("[BuildJsonBuilder] from, to and copy cannot be null");
                }
            } else {
                if (item.from != null && item.to != null) {
                    throw new CEIException("[BuildJsonBuilder] from, to cannot exist with copy");
                } else {
                    item.from = "{" + item.copy + "}";
                    item.to = item.copy;
                    item.copy = null;
                }
            }
            Variable from = getFromVariable(item, methodBuilder);
            if (from == null) {
                throw new CEIException("[BuildJsonBuilder] cannot process from");
            }
            Variable to = VariableFactory.createHardcodeStringVariable(item.to);

            if (item instanceof xJsonAuto) {
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
            item.endBuilding();
        });
        return jsonObject;
    }

    private static Variable buildStringBuilder(xAttribute valueProcessor, StringBuilderBuilder builder) {
            return null;
    }

    private static Variable getFromVariable(xJsonType jsonItem, MethodBuilder method) {
        if (jsonItem.from != null && !jsonItem.from.equals("")) {
            return method.queryVariableAsParam(jsonItem.from);
        }
        return null;
    }
}
