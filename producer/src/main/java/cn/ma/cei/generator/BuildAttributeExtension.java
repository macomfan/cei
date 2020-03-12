package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.IMethodBuilder;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IJsonBuilderBuilder;
import cn.ma.cei.generator.builder.IStringBuilderBuilder;
import cn.ma.cei.generator.buildin.JsonWrapper;
import cn.ma.cei.model.base.xAttributeExtension;
import cn.ma.cei.model.json.*;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.model.xAttribute;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.ReflectionHelper;
import cn.ma.cei.utils.RegexHelper;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class BuildAttributeExtension {
    public static Variable createValueFromAttribute(String attrName, xAttributeExtension various, IMethodBuilder methodBuilder) {
        Checker.isNull(methodBuilder, BuildAttributeExtension.class, "IMethodBuilder");
        IDataProcessorBuilder implementationBuilder = methodBuilder.createDataProcessorBuilder();
        Checker.isNull(implementationBuilder, BuildAttributeExtension.class, "IDataProcessorBuilder");
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
                IJsonBuilderBuilder jsonBuilderBuilder = implementationBuilder.createJsonBuilderBuilder();
                return buildJsonBuilder(valueProcessor.jsonBuilder, jsonBuilderBuilder);
            } else if (valueProcessor.stringBuilder != null) {
                return buildStringBuilder(valueProcessor, null);
            } else {
                return null;
            }

        } else if (RegexHelper.isReference(attrValue) != null) {
                Variable var = GlobalContext.getCurrentMethod().getVariable(RegexHelper.isReference(attrValue));
                if (var == null) {
                    throw new CEIException("Cannot lookup variable: " + var);
                }
                return var;
        } else {
            List<String> linkedParam = RegexHelper.findReference(attrValue);
            if (linkedParam.isEmpty()) {
                return GlobalContext.createStringConstant(attrValue);
            } else {
                IStringBuilderBuilder stringBuilderBuilder = implementationBuilder.createStringBuilderBuilder();
                if (stringBuilderBuilder == null) {
                    throw new CEIException("StringBuilderBuilder is null");
                }
                List<Variable> variables = new LinkedList<>();
                variables.add(GlobalContext.createStringConstant(attrValue));
                linkedParam.forEach(item -> {
                    Variable param = GlobalContext.getCurrentMethod().getVariable(item);
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

    private static Variable buildJsonBuilder(xJsonBuilder jsonBuilder, IJsonBuilderBuilder jsonBuilderBuilder) {
        if (jsonBuilderBuilder == null) {
            throw new CEIException("JsonBuilderBuilder is null");
        }
        Variable jsonObject = GlobalContext.getCurrentMethod().createLocalVariable(JsonWrapper.getType(), "jsonBuilder");
        jsonBuilderBuilder.defineRootJsonObject(jsonObject);
        jsonBuilder.itemList.forEach(item -> item.doBuild(() ->{
            if (item.copy == null) {
                if (item.key == null || item.value == null) {
                    throw new CEIException("[BuildJsonBuilder] from, to and copy cannot be null");
                }
            } else {
                if (item.key != null && item.value != null) {
                    throw new CEIException("[BuildJsonBuilder] from, to cannot exist with copy");
                } else {
                    item.key = "{" + item.copy + "}";
                    item.value = item.copy;
                    item.copy = null;
                }
            }
            Variable from = getFromVariable(item);
            if (from == null) {
                throw new CEIException("[BuildJsonBuilder] cannot process from");
            }
            Variable to = GlobalContext.createStringConstant(item.value);

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

    private static Variable buildStringBuilder(xAttribute valueProcessor, IStringBuilderBuilder builder) {
            return null;
    }

    private static Variable getFromVariable(xJsonType jsonItem) {
        if (jsonItem.key != null && !jsonItem.key.equals("")) {
            return GlobalContext.getCurrentMethod().getVariableAsParam(jsonItem.key);
        }
        return null;
    }
}
