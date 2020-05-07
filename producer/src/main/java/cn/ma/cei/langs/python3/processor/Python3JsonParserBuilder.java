/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.python3.processor;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IJsonCheckerBuilder;
import cn.ma.cei.generator.builder.IJsonParserBuilder;
import cn.ma.cei.generator.buildin.JsonWrapper;
import cn.ma.cei.generator.buildin.TheArray;
import cn.ma.cei.langs.python3.tools.Python3Method;

/**
 *
 * @author U0151316
 */
public class Python3JsonParserBuilder implements IJsonParserBuilder {

    private final Python3Method method;

    public Python3JsonParserBuilder(Python3Method method) {
        this.method = method;
    }

    @Override
    public void getJsonString(Variable value, Variable jsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".get_string_or_none", key));
        } else {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".get_string", key));
        }

    }

    @Override
    public void getJsonInteger(Variable value, Variable jsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".get_int_or_none", key));
        } else {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".get_int", key));
        }

    }

    @Override
    public void getJsonBoolean(Variable value, Variable jsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".get_bool_or_none", key));
        } else {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".get_bool", key));
        }

    }

    @Override
    public void getJsonDecimal(Variable value, Variable jsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".get_decimal_or_none", key));
        } else {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".get_decimal", key));
        }

    }

    @Override
    public void assignJsonStringArray(Variable value, Variable jsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".get_string_array_or_none", key));
        }
        else {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".get_string_array", key));
        }
    }

    @Override
    public void assignJsonDecimalArray(Variable value, Variable jsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".get_decimal_array_or_none", key));
        }
        else {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".get_decimal_array", key));
        }
    }

    @Override
    public void assignJsonBooleanArray(Variable value, Variable jsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".get_bool_array_or_none", key));
        }
        else {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".get_bool_array", key));
        }
    }

    @Override
    public void assignJsonIntArray(Variable value, Variable jsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".get_int_array_or_none", key));
        }
        else {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".get_int_array", key));
        }
    }

    @Override
    public void getJsonArray(Variable jsonObject, Variable parentJsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.defineVariable(jsonObject), method.invoke(parentJsonObject.getDescriptor() + ".get_array_or_none", key));
        } else {
            method.addAssign(method.defineVariable(jsonObject), method.invoke(parentJsonObject.getDescriptor() + ".get_array", key));
        }
    }

    @Override
    public void getJsonObject(Variable jsonObject, Variable parentJsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.defineVariable(jsonObject), method.invoke(parentJsonObject.getDescriptor() + ".get_object_or_none", key));
        } else {
            method.addAssign(method.defineVariable(jsonObject), method.invoke(parentJsonObject.getDescriptor() + ".get_object", key));
        }
    }


    @Override
    public void assignModel(Variable to, Variable model) {
        if (to != null) {
            method.addAssign(method.useVariable(to), method.useVariable(model));
        }
    }

    @Override
    public void startJsonObjectArray(Variable eachItemJsonObject, Variable jsonObject) {
        method.startFor(eachItemJsonObject, jsonObject.getDescriptor() + ".array()" + ":");
    }

    @Override
    public void endJsonObjectArray(Variable value, Variable model) {
        method.startIf(value.getDescriptor() + " is None");
        method.addAssign(method.useVariable(value), method.newInstance(TheArray.getType()));
        method.endIf();
        method.addInvoke(value.getDescriptor() + ".append", model);
        method.endFor();
    }

    @Override
    public void defineModel(Variable model) {
        method.addAssign(method.defineVariable(model), method.newInstance(model.getType()));
    }

    @Override
    public void defineRootJsonObject(Variable jsonObject, Variable stringVariable) {
        method.addAssign(method.defineVariable(jsonObject), method.invoke(JsonWrapper.getType().getDescriptor() + ".parse_from_string", stringVariable));
    }

    @Override
    public IJsonCheckerBuilder createJsonCheckerBuilder() {
        return new Python3JsonCheckerBuilder(method);
    }

}
