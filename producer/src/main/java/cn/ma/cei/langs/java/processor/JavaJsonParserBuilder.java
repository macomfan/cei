package cn.ma.cei.langs.java.processor;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IJsonCheckerBuilder;
import cn.ma.cei.generator.builder.IJsonParserBuilder;
import cn.ma.cei.langs.java.buildin.TheLinkedList;
import cn.ma.cei.langs.java.tools.JavaMethod;

public class JavaJsonParserBuilder implements IJsonParserBuilder {

    private final JavaMethod method;

    public JavaJsonParserBuilder(JavaMethod method) {
        this.method = method;
    }

    @Override
    public void getJsonString(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.getDescriptor() + ".getString", itemName));
    }

    @Override
    public void getJsonInteger(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.getDescriptor() + ".getLong", itemName));
    }

    @Override
    public void assignJsonStringArray(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.getDescriptor() + ".getStringArray", itemName));
    }

    @Override
    public void assignJsonDecimalArray(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.getDescriptor() + ".getDecimalArray", itemName));
    }

    @Override
    public void assignJsonBooleanArray(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.getDescriptor() + ".getBooleanArray", itemName));
    }

    @Override
    public void assignJsonIntArray(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.getDescriptor() + ".getIntArray", itemName));
    }

    @Override
    public void getJsonArray(Variable jsonWrapperObject, Variable jsonObject, Variable itemName) {
        method.addAssign(method.defineVariable(jsonWrapperObject), method.invoke(jsonObject.getDescriptor() + ".getArray", itemName));
    }

    @Override
    public void defineJsonObject(Variable jsonObject, Variable parentJsonObject, Variable itemName) {
        method.addAssign(method.defineVariable(jsonObject), method.invoke(parentJsonObject.getDescriptor() + ".getObject", itemName));
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
    public void endJsonObjectArray(Variable to, Variable model) {
        method.startIf(to.getDescriptor() + " == null");
        method.addAssign(method.useVariable(to), method.newInstance(TheLinkedList.getType()));
        method.endIf();
        method.addInvoke(to.getDescriptor() + ".add", model);
        method.endFor();
    }

    @Override
    public void defineModel(Variable model) {
        method.addAssign(method.defineVariable(model), method.newInstance(model.getType()));
    }

    @Override
    public void defineRootJsonObject(Variable jsonObject, Variable responseVariable) {
        Variable value = BuilderContext.createStatement(responseVariable.getDescriptor() + ".getJson()");
        method.addAssign(method.defineVariable(jsonObject), method.useVariable(value));
    }

    @Override
    public void getJsonBoolean(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.getDescriptor() + ".getBoolean", itemName));
    }

    @Override
    public void getJsonDecimal(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.getDescriptor() + ".getDecimal", itemName));
    }

    @Override
    public IJsonCheckerBuilder createJsonCheckerBuilder() {
        return new JavaJsonCheckerBuilder(method);
    }
}
