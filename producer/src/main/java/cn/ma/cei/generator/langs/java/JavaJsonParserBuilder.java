package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.builder.JsonParserBuilder;
import cn.ma.cei.generator.langs.java.tools.JavaMethod;

public class JavaJsonParserBuilder extends JsonParserBuilder {

    private final JavaMethod method;

    public JavaJsonParserBuilder(JavaMethod method) {
        this.method = method;
    }

    @Override
    public void getJsonString(Variable to, Variable jsonObject, String itemName) {
        method.getCode().appendStatementWordsln(
                to.nameDescriptor, "=", jsonObject.nameDescriptor + ".getString(" + method.getCode().toJavaString(itemName) + ")");
    }

    @Override
    public void getJsonInteger(Variable to, Variable jsonObject, String itemName) {
        method.getCode().appendStatementWordsln(
                to.nameDescriptor, "=", jsonObject.nameDescriptor + ".getInteger(" + method.getCode().toJavaString(itemName) + ")");
    }

    @Override
    public void getJsonStringArray(Variable to, Variable jsonObject, String itemName) {
        method.getCode().appendStatementWordsln(to.nameDescriptor, "=", jsonObject.nameDescriptor + ".getStringArray(" + method.getCode().toJavaString(itemName) + ")");
    }

    @Override
    public void getJsonObject(Variable to, Variable parentModel, Variable jsonObject, Variable parentJsonObject, String itemName) {
        method.getCode().appendStatementWordsln(
                jsonObject.type.getDescriptor(), jsonObject.nameDescriptor, "=", parentJsonObject.nameDescriptor + ".getObject(" + method.getCode().toJavaString(itemName) + ")");
        if (to != null) {
            method.getCode().appendStatementWordsln(to.nameDescriptor, "=", parentModel.nameDescriptor);
        }
    }

    @Override
    public void startJsonObjectArrayLoop(Variable eachItemJsonObject, Variable parentJsonObject, String itemName) {
        method.getCode().appendWordsln("for (" + eachItemJsonObject.type.getDescriptor(), eachItemJsonObject.nameDescriptor, ":", parentJsonObject.nameDescriptor + ".getObjectArray(" + method.getCode().toJavaString(itemName) + ")) {");
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
        this.method.getCode().appendStatementWordsln(
                model.type.getDescriptor(),
                model.nameDescriptor,
                "= new",
                model.type.getDescriptor() + "()");
    }

    @Override
    public void defineRootJsonObject(Variable jsonObject, Variable responseVariable) {
        this.method.getCode().appendStatementWordsln(
                jsonObject.type.getDescriptor(),
                jsonObject.nameDescriptor,
                "=",
                responseVariable.nameDescriptor + ".getJson()");
    }

    @Override
    public void getJsonLong(Variable to, Variable jsonObject, String itemName) {
        method.getCode().appendStatementWordsln(
                to.nameDescriptor, "=", jsonObject.nameDescriptor + ".getLong(" + method.getCode().toJavaString(itemName) + ")");
    }

    @Override
    public void getJsonBoolean(Variable to, Variable jsonObject, String itemName) {
        method.getCode().appendStatementWordsln(
                to.nameDescriptor, "=", jsonObject.nameDescriptor + ".getBoolean(" + method.getCode().toJavaString(itemName) + ")");
    }

    @Override
    public void getJsonDecimal(Variable to, Variable jsonObject, String itemName) {
        method.getCode().appendStatementWordsln(
                to.nameDescriptor, "=", jsonObject.nameDescriptor + ".getDecimal(" + method.getCode().toJavaString(itemName) + ")");
    }
}
