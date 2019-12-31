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
    public void getNow(Variable output, Variable format) {
        method.newInstanceWithInvoke(output, "SignatureTool.getNow", format);
    }

    @Override
    public void addQueryString(Variable requestVariable, Variable key, Variable value) {
        method.invoke(requestVariable.nameDescriptor + ".addQueryString", key, value);
    }

    @Override
    public void combineQueryString(Variable requestVariable, Variable output, Variable sort, Variable separator) {
        method.newInstanceWithInvoke(output, "SignatureTool.combineQueryString", requestVariable, sort, separator);
    }

    @Override
    public void getRequestInfo(Variable requestVariable, Variable output, Variable info, Variable convert) {
        method.newInstanceWithInvoke(output, "SignatureTool.getRequestInfo", requestVariable, info, convert);
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
    public void addStringArray(Variable output, Variable input) {
        method.assignWithInvoke(output, "SignatureTool.addStringArray", output, input);
    }

    @Override
    public void newStringArray(Variable stringArray) {
        parent.addReference(xStringArray.inst.getType());
        method.getCode().appendStatementWordsln("List<String>", stringArray.nameDescriptor, "=", "new", "LinkedList<>()");
    }

    @Override
    public void combineStringArray(Variable output, Variable input, Variable separator) {
        method.newInstanceWithInvoke(output, "SignatureTool.combineStringArray", input, separator);
    }

    @Override
    public void base64(Variable output, Variable input) {
        method.newInstanceWithInvoke(output, "SignatureTool.base64", input);
    }

    @Override
    public void hmacsha265(Variable output, Variable input, Variable key) {
        method.newInstanceWithInvoke(output, "SignatureTool.hmacsha256", input, key);
    }

    @Override
    public void appendToString(boolean needDefineNewOutput, Variable output, Variable input) {
        if (needDefineNewOutput) {
            method.newInstanceWithValue(output, input);
        } else {
            method.assignWithInvoke(output, "SignatureTool.appendToString", output, input);
        }
    }
}
