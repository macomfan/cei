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
        IDataProcessorBuilder dataProcessorBuilder = methodBuilder.createDataProcessorBuilder();
        Checker.isNull(dataProcessorBuilder, BuildAttributeExtension.class, "IDataProcessorBuilder");
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
            // not define the attribute, should read <attribute> session
            xAttribute valueProcessor = various.getAttributeByName(attrName);
            if (valueProcessor == null) {
                throw new CEIException("Attribute " + attrName + " do not have both value and value extension");
            }
            return BuildDataProcessor.build(valueProcessor.items, null, valueProcessor.result, dataProcessorBuilder);
//            if (valueProcessor.jsonBuilder != null) {
//                IJsonBuilderBuilder jsonBuilderBuilder = dataProcessorBuilder.createJsonBuilderBuilder();
//                Variable jsonBuilder = buildJsonBuilder(valueProcessor.jsonBuilder, jsonBuilderBuilder);
//                return dataProcessorBuilder.jsonBuilderToString(jsonBuilder);
//            } else if (valueProcessor.stringBuilder != null) {
//                return buildStringBuilder(valueProcessor, null);
//            } else {
//                return null;
//            }

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
                return dataProcessorBuilder.stringReplacement(params);
            }
        }
    }
}
