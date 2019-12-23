package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.builder.JsonParserBuilder;
import cn.ma.cei.generator.langs.java.tools.JavaMethod;

public class JavaJsonParserBuilder extends JsonParserBuilder {

    private JavaMethod method;

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
    public void getJsonObject(Variable jsonObject, Variable parentJsonObject, String itemName) {
        method.getCode().appendStatementWordsln(
                jsonObject.type.getDescriptor(), jsonObject.nameDescriptor, "=", parentJsonObject.nameDescriptor + ".getObject(" + method.getCode().toJavaString(itemName) + ")");
    }

    @Override
    public void getJsonObjectArray(Variable jsonObject, Variable parentJsonObject, String itemName) {
        method.getCode().appendStatementWordsln(
                jsonObject.type.getDescriptor(), jsonObject.nameDescriptor, "=", parentJsonObject.nameDescriptor + ".getArray(" + method.getCode().toJavaString(itemName) + ")");
    }

    @Override
    public void startJsonObjectArrayLoop(Variable eachItemJsonObject, Variable parentJsonObject) {
        method.getCode().appendWordsln("for(" + eachItemJsonObject.type.getDescriptor(), eachItemJsonObject.nameDescriptor, "in", parentJsonObject.nameDescriptor + ".getItems()) {");
        method.getCode().startBlock();

    }

    @Override
    public void endJsonObjectArrayLoop(Variable to, Variable model) {
        method.getCode().appendWordsln("if, (" + to.nameDescriptor, "==", "null) {");
        method.getCode().newBlock(() -> method.getCode().appendStatementWordsln(to.nameDescriptor, "=", "new", to.type.getDescriptor()));
        method.getCode().appendln("}");
        method.getCode().appendStatementWordsln(to.nameDescriptor + ".add(" + model.nameDescriptor + ")");
        method.getCode().endBlock();
        method.getCode().appendStatementln("}");
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
                "= new",
                jsonObject.type.getDescriptor() + "(" + responseVariable.nameDescriptor + ")");
    }
}
