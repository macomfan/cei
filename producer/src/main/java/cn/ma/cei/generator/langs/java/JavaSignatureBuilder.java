/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.builder.SignatureBuilder;
import cn.ma.cei.generator.buildin.SignatureTool;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableList;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.langs.java.tools.JavaClass;
import cn.ma.cei.generator.langs.java.tools.JavaMethod;
import cn.ma.cei.model.types.xStringArray;

/**
 *
 * @author u0151316
 */
public class JavaSignatureBuilder extends SignatureBuilder {

    private JavaMethod method;
    private JavaClass parent;

    public JavaSignatureBuilder(JavaClass javaClass) {
        parent = javaClass;
    }

    @Override
    public void getNow(Variable output, String format) {
        method.getCode().appendStatementWordsln(
                output.type.getDescriptor(),
                output.nameDescriptor, "=", "SignatureTool.getNow(" + method.getCode().toJavaString(format) + ")");
    }

    @Override
    public void appendQueryString(Variable requestVariable, String key, Variable value) {
        method.getCode().appendStatementln(
                requestVariable.nameDescriptor
                + ".addQueryString("
                + method.getCode().toJavaString(key) + ", "
                + value.nameDescriptor + ")");
    }

    @Override
    public void combineQueryString(Variable requestVariable, Variable output, String sort, String separator) {
        method.getCode().appendStatementWordsln(
                output.type.getDescriptor(),
                output.nameDescriptor,
                "=",
                "SignatureTool.combineQueryString(" + requestVariable.nameDescriptor + ",",
                sort + ",",
                method.getCode().toJavaString(separator) + ")");
    }

    @Override
    public void getRequestInfo(Variable requestVariable, Variable output, String infoDescriptor, String convertDescriptor) {
        method.getCode().appendStatementWordsln(
                output.type.getDescriptor(),
                output.nameDescriptor,
                "=",
                "SignatureTool.getRequestInfo(" + requestVariable.nameDescriptor + ",",
                infoDescriptor + ",",
                convertDescriptor + ")");
    }

    @Override
    public void onAddReference(VariableType variableType) {
        parent.addReference(variableType);
    }

    @Override
    public void startMethod(VariableType returnType, String methodDescriptor, VariableList params) {
        method = new JavaMethod(parent);
        parent.addReference(SignatureTool.getType());
        method.startStaticMethod(null, methodDescriptor, params);
    }

    @Override
    public void endMethod() {
        method.endMethod();
        parent.addMethod(method.getCode());
    }

    @Override
    public void appendStringArray(Variable output, Variable input) {
        method.getCode().appendStatementWordsln(output.nameDescriptor, "=",
                "SignatureTool.appendStringArray(" + output.nameDescriptor + ",", input.nameDescriptor + ")");
    }

    @Override
    public void newStringArray(Variable stringArray) {
        parent.addReference(xStringArray.inst.getType());
        method.getCode().appendStatementWordsln("List<String>", stringArray.nameDescriptor, "=", "new", "LinkedList<>()");
    }
}
