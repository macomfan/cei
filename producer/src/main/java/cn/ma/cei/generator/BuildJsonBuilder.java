/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.JsonBuilderBuilder;
import cn.ma.cei.generator.builder.MethodBuilder;
import cn.ma.cei.generator.buildin.JsonWrapper;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.model.json.xJsonAuto;
import cn.ma.cei.model.json.xJsonBoolean;
import cn.ma.cei.model.json.xJsonBuilder;
import cn.ma.cei.model.json.xJsonDecimal;
import cn.ma.cei.model.json.xJsonInteger;
import cn.ma.cei.model.json.xJsonLong;
import cn.ma.cei.model.json.xJsonString;
import cn.ma.cei.model.json.xJsonType;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.model.types.xString;

/**
 *
 * @author u0151316
 */
public class BuildJsonBuilder {

    public static Variable build(xJsonBuilder jsonBuilder, JsonBuilderBuilder jsonBuilderBuilder, MethodBuilder method) {
        Variable jsonObject = method.newLocalVariable(JsonWrapper.getType(), "{jsonBuilder}");
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
            Variable from = getFromVariable(item, method);
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
            } else if (item instanceof xJsonLong) {
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

    private static Variable getFromVariable(xJsonType jsonItem, MethodBuilder method) {
        if (jsonItem.from != null && !jsonItem.from.equals("")) {
            return method.queryVariableAsParam(jsonItem.from);
        }
        return null;
    }
}
