package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.builder.JsonParserBuilder;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.langs.java.tools.JavaMethod;

public class JavaJsonParserBuilder extends JsonParserBuilder {

    private final JavaMethod method;

    public JavaJsonParserBuilder(JavaMethod method) {
        this.method = method;
    }

    @Override
    public void getJsonString(Variable to, Variable jsonObject, Variable itemName) {
        method.assignWithInvoke(to, jsonObject.nameDescriptor + ".getString", itemName);
    }

    @Override
    public void getJsonInteger(Variable to, Variable jsonObject, Variable itemName) {
        method.assignWithInvoke(to, jsonObject.nameDescriptor + ".getInteger", itemName);
    }

    @Override
    public void getJsonStringArray(Variable to, Variable jsonObject, Variable itemName) {
        method.assignWithInvoke(to, jsonObject.nameDescriptor + ".getStringArray", itemName);
    }

    @Override
    public void getJsonObject(Variable to, Variable parentModel, Variable jsonObject, Variable parentJsonObject, Variable itemName) {
        method.newInstanceWithInvoke(jsonObject, parentJsonObject.nameDescriptor + ".getObject", itemName);
        if (to != null) {
            method.assign(to, parentModel);
        }
    }

    @Override
    public void startJsonObjectArrayLoop(Variable eachItemJsonObject, Variable parentJsonObject, Variable itemName) {
        method.getCode().appendWordsln("for (" + eachItemJsonObject.type.getDescriptor(), eachItemJsonObject.nameDescriptor, ":", parentJsonObject.nameDescriptor + ".getObjectArray(" + itemName.nameDescriptor + ")) {");
        method.getCode().startBlock();
    }

    @Override
    public void endJsonObjectArrayLoop(Variable to, Variable model) {
        method.getCode().appendWordsln("if (" + to.nameDescriptor, "==", "null) {");
        method.getCode().newBlock(() -> method.newListVariable(to));
        method.getCode().appendln("}");
        method.getCode().appendStatementWordsln(to.nameDescriptor + ".add(" + model.nameDescriptor + ")");
        method.getCode().endBlock();
        method.getCode().appendln("}");
    }

    @Override
    public void defineModel(Variable model) {
        method.newInstance(model);
    }

    @Override
    public void defineRootJsonObject(Variable jsonObject, Variable responseVariable) {
        Variable value = VariableFactory.createConstantVariable(responseVariable.nameDescriptor + ".getJson()");
        method.newInstanceWithValue(jsonObject, value);
    }

    @Override
    public void getJsonLong(Variable to, Variable jsonObject, Variable itemName) {
        method.assignWithInvoke(to, jsonObject.nameDescriptor + ".getLong", itemName);
    }

    @Override
    public void getJsonBoolean(Variable to, Variable jsonObject, Variable itemName) {
        method.assignWithInvoke(to, jsonObject.nameDescriptor + ".getBoolean", itemName);
    }

    @Override
    public void getJsonDecimal(Variable to, Variable jsonObject, Variable itemName) {
        method.assignWithInvoke(to, jsonObject.nameDescriptor + ".getDecimal", itemName);
    }
}
