package cn.ma.cei.langs.java.processor;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IJsonCheckerBuilder;
import cn.ma.cei.generator.builder.IJsonParserBuilder;
import cn.ma.cei.generator.buildin.JsonWrapper;
import cn.ma.cei.langs.java.buildin.TheLinkedList;
import cn.ma.cei.langs.java.tools.JavaMethod;

public class JavaJsonParserBuilder implements IJsonParserBuilder {

    private final JavaMethod method;

    public JavaJsonParserBuilder(JavaMethod method) {
        this.method = method;
    }

    @Override
    public void getJsonString(Variable value, Variable jsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getStringOrNull", key));
        } else {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getString", key));
        }
    }

    @Override
    public void getJsonInteger(Variable value, Variable jsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getLongOrNull", key));
        }
        else {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getLong", key));
        }
    }

    @Override
    public void assignJsonStringArray(Variable value, Variable jsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getStringArrayOrNull", key));
        } else {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getStringArray", key));
        }

    }

    @Override
    public void assignJsonDecimalArray(Variable value, Variable jsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getDecimalArrayOrNull", key));
        } else {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getDecimalArray", key));
        }

    }

    @Override
    public void assignJsonBooleanArray(Variable value, Variable jsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getBooleanArray", key));
        } else {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getBooleanArrayOrNull", key));
        }

    }

    @Override
    public void assignJsonIntArray(Variable value, Variable jsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getIntArray", key));
        } else {
            method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".getIntArrayOrNull", key));
        }

    }

    @Override
    public void getJsonArray(Variable jsonObject, Variable parentJsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.defineVariable(jsonObject), method.invoke(parentJsonObject.getDescriptor() + ".getArrayOrNull", key));
        } else {
            method.addAssign(method.defineVariable(jsonObject), method.invoke(parentJsonObject.getDescriptor() + ".getArray", key));
        }
    }

    @Override
    public void getJsonObject(Variable jsonObject, Variable parentJsonObject, Variable key, boolean optional) {
        if (optional) {
            method.addAssign(method.defineVariable(jsonObject), method.invoke(parentJsonObject.getDescriptor() + ".getObjectOrNull", key));
        } else {
            method.addAssign(method.defineVariable(jsonObject), method.invoke(parentJsonObject.getDescriptor() + ".getObject", key));
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
        method.startFor(eachItemJsonObject, jsonObject.getDescriptor());
    }

    @Override
    public void endJsonObjectArray(Variable value, Variable model) {
        method.startIf(value.getDescriptor() + " == null");
        method.addAssign(method.useVariable(value), method.newInstance(TheLinkedList.getType()));
        method.endIf();
        method.addInvoke(value.getDescriptor() + ".add", model);
        method.endFor();
    }

    @Override
    public void defineModel(Variable model) {
        method.addAssign(method.defineVariable(model), method.newInstance(model.getType()));
    }

    @Override
    public void defineRootJsonObject(Variable jsonObject, Variable stringVariable) {
        method.addAssign(method.defineVariable(jsonObject), method.invoke(JsonWrapper.getType().getDescriptor() + ".parseFromString", stringVariable));
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

    @Override
    public IJsonCheckerBuilder createJsonCheckerBuilder() {
        return new JavaJsonCheckerBuilder(method);
    }
}
