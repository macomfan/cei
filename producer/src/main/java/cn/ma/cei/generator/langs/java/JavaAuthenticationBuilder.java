/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IAuthenticationBuilder;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.buildin.AuthenticationTool;
import cn.ma.cei.generator.langs.java.buildin.TheLinkedList;
import cn.ma.cei.generator.langs.java.tools.JavaClass;
import cn.ma.cei.generator.langs.java.tools.JavaMethod;
import cn.ma.cei.model.types.xStringArray;

import java.util.List;

/**
 * @author u0151316
 */
public class JavaAuthenticationBuilder implements IAuthenticationBuilder {

    private JavaMethod method;
    private JavaClass parent;

    public JavaAuthenticationBuilder(JavaClass javaClass) {
        parent = javaClass;
    }







    @Override
    public void onAddReference(VariableType variableType) {
        parent.addReference(variableType);
    }


    @Override
    public void startMethod(VariableType returnType, String methodDescriptor, List<Variable> params) {
        method = new JavaMethod(parent);
        parent.addReference(AuthenticationTool.getType());
        method.startStaticMethod(null, methodDescriptor, params);
    }

    @Override
    public IDataProcessorBuilder createDataProcessorBuilder() {
        return new JavaDataProcessorBuilder(method);
    }

    @Override
    public void endMethod() {
        method.endMethod();
        parent.addMethod(method);
    }

    @Override
    public void addStringArray(Variable output, Variable input) {
        method.addAssign(method.useVariable(output), method.invoke("AuthenticationTool.addStringArray", output, input));
    }

    @Override
    public void newStringArray(Variable stringArray) {
        parent.addReference(xStringArray.inst.getType());
        parent.addReference(TheLinkedList.getType());
        method.addAssign("List<String> " + stringArray.getDescriptor(), "new LinkedList<>()");
    }

    @Override
    public void combineStringArray(Variable output, Variable input, Variable separator) {
        method.addAssign(method.defineVariable(output), method.invoke("AuthenticationTool.combineStringArray", input, separator));
    }

    @Override
    public void appendToString(boolean needDefineNewOutput, Variable output, Variable input) {
        if (needDefineNewOutput) {
            method.addAssign(method.defineVariable(output), method.useVariable(input));
        } else {
            method.addAssign(method.useVariable(output), method.invoke("AuthenticationTool.appendToString", output, input));
        }
    }
}
