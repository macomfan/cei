/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.cpp.processor;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IJsonCheckerBuilder;
import cn.ma.cei.generator.builder.IJsonParserBuilder;
import cn.ma.cei.generator.buildin.JsonWrapper;
import cn.ma.cei.langs.cpp.tools.CppMethod;

/**
 *
 * @author U0151316
 */
public class CppJsonParserBuilder implements IJsonParserBuilder {

    private CppMethod method;

    public CppJsonParserBuilder(CppMethod method) {
        this.method = method;
    }

    @Override
    public void getJsonString(Variable value, Variable jsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getStringOrDefault", key));
        } else {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getString", key));
        }
    }

    @Override
    public void getJsonInteger(Variable value, Variable jsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getLongOrDefault", key));
        }
        else {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getLong", key));
        }
    }

    @Override
    public void defineModel(Variable model) {
        method.addDefineStackVariable(model);
    }

    @Override
    public void defineRootJsonObject(Variable jsonObject, Variable stringVariable) {
        method.addAssign(method.declareStackVariable(jsonObject), method.invoke(JsonWrapper.getType().getDescriptor() + ".parseFromString", stringVariable));
    }

    @Override
    public IJsonCheckerBuilder createJsonCheckerBuilder() {
        return new CppJsonCheckerBuilder();
    }

    @Override
    public void assignJsonStringArray(Variable value, Variable jsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getStringArrayOrDefault", key));
        } else {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getStringArray", key));
        }
    }

    @Override
    public void assignJsonDecimalArray(Variable value, Variable jsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getDecimalArrayOrDefault", key));
        } else {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getDecimalArray", key));
        }
    }

    @Override
    public void assignJsonBooleanArray(Variable value, Variable jsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getBooleanArrayOrDefault", key));
        } else {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getBooleanArray", key));
        }
    }

    @Override
    public void assignJsonIntArray(Variable value, Variable jsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getIntArrayOrDefault", key));
        } else {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getIntArray", key));
        }
    }

    @Override
    public void getJsonArray(Variable jsonObject, Variable parentJsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.declareStackVariable(jsonObject), method.invoke(parentJsonObject.getDescriptor() + ".getArrayOrNull", key));
        } else {
            method.addAssign(method.declareStackVariable(jsonObject), method.invoke(parentJsonObject.getDescriptor() + ".getArray", key));
        }
    }


    @Override
    public void getJsonObject(Variable jsonObject, Variable parentJsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.declareStackVariable(jsonObject), method.invoke(parentJsonObject.getDescriptor() + ".getObjectOrNull", key));
        } else {
            method.addAssign(method.declareStackVariable(jsonObject), method.invoke(parentJsonObject.getDescriptor() + ".getObject", key));
        }
    }

    @Override
    public void assignModel(Variable value, Variable model) {
        if (value != null) {
            method.addAssign(method.useVariable(value), method.useVariable(model));
        }
    }

    @Override
    public void startJsonObjectArray(Variable eachItemJsonObject, Variable jsonObject) {
        method.startFor(eachItemJsonObject, method.invoke(jsonObject.getDescriptor() + ".Array"));
    }

    @Override
    public void endJsonObjectArray(Variable value, Variable model) {
        method.addAssign(method.useVariable(value), method.invoke(value.getDescriptor() + ".push_back", model));
        method.endFor();
    }

    @Override
    public void getJsonBoolean(Variable value, Variable jsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getBooleanOrNull", key));
        } else {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getBoolean", key));
        }
    }

    @Override
    public void getJsonDecimal(Variable value, Variable jsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getDecimalOrNull", key));
        }
        else {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getDecimal", key));
        }
    }
}
