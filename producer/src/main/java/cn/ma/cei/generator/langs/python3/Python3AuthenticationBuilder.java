/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.python3;

import cn.ma.cei.generator.builder.*;
import cn.ma.cei.generator.buildin.AuthenticationTool;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.langs.python3.tools.Python3Class;
import cn.ma.cei.generator.langs.python3.tools.Python3Method;
import cn.ma.cei.model.types.xStringArray;
import java.util.List;

/**
 *
 * @author u0151316
 */
public class Python3AuthenticationBuilder implements IAuthenticationBuilder {

    private Python3Method method;
    private Python3Class parent;

    public Python3AuthenticationBuilder(Python3Class parent) {
        this.parent = parent;
    }

    @Override
    public void newStringArray(Variable stringArray) {
        parent.addReference(xStringArray.inst.getType());
        method.addAssign(stringArray.getDescriptor(), "list()");
        //method.getCode().appendStatementWordsln("List<String>", stringArray.getNameDescriptor(), "=", "new", "LinkedList<>()");
    }




    @Override
    public void appendToString(boolean needDefineNewOutput, Variable output, Variable input) {
        if (needDefineNewOutput) {
            method.addAssign(method.defineVariable(output), method.useVariable(input));
        } else {
            method.addAssign(method.useVariable(output), method.invoke("AuthenticationTool.append_to_string", output, input));
        }
    }


    @Override
    public void addStringArray(Variable output, Variable input) {
        method.addAssign(method.useVariable(output), method.invoke("AuthenticationTool.add_string_array", output, input));
    }

    @Override
    public void combineStringArray(Variable output, Variable input, Variable separator) {
        method.addAssign(method.defineVariable(output), method.invoke("AuthenticationTool.combine_string_array", input, separator));
    }

    @Override
    public void onAddReference(VariableType variableType) {
        parent.addReference(xStringArray.inst.getType());
    }

    @Override
    public void startMethod(VariableType returnType, String methodDescriptor, List<Variable> params) {
        method = new Python3Method(parent);
        parent.addReference(AuthenticationTool.getType());
        method.startStaticMethod(null, methodDescriptor, params);
    }

    @Override
    public IDataProcessorBuilder createDataProcessorBuilder() {
        return null;
    }

    @Override
    public void endMethod() {
        method.endMethod();
        parent.addMethod(method);
    }

}