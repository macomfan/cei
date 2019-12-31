/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.python3;

import cn.ma.cei.generator.builder.SignatureBuilder;
import cn.ma.cei.generator.buildin.SignatureTool;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableList;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.langs.python3.tools.Python3Class;
import cn.ma.cei.generator.langs.python3.tools.Python3Method;
import cn.ma.cei.model.types.xStringArray;

/**
 *
 * @author u0151316
 */
public class Python3SignatureBuilder extends SignatureBuilder {

    private Python3Method method;
    private Python3Class parent;

    public Python3SignatureBuilder(Python3Class parent) {
        this.parent = parent;
    }

    @Override
    public void newStringArray(Variable stringArray) {
        parent.addReference(xStringArray.inst.getType());
        // TODO
        //method.getCode().appendStatementWordsln("List<String>", stringArray.nameDescriptor, "=", "new", "LinkedList<>()");
    }

    @Override
    public void getNow(Variable output, Variable format) {
        method.newInstanceWithInvoke(output, "SignatureTool.get_now", format);
    }

    @Override
    public void addQueryString(Variable requestVariable, Variable key, Variable value) {
        method.invoke(requestVariable.nameDescriptor + ".add_query_string", key, value);
    }

    @Override
    public void appendToString(boolean needDefineNewOutput, Variable output, Variable input) {
        if (needDefineNewOutput) {
            method.newInstanceWithValue(output, input);
        } else {
            method.assignWithInvoke(output, "SignatureTool.append_to_string", output, input);
        }
    }

    @Override
    public void combineQueryString(Variable requestVariable, Variable output, Variable sort, Variable separator) {
        method.newInstanceWithInvoke(output, "SignatureTool.combine_query_string", requestVariable, sort, separator);
    }

    @Override
    public void getRequestInfo(Variable requestVariable, Variable output, Variable info, Variable convert) {
        method.newInstanceWithInvoke(output, "SignatureTool.get_request_info", requestVariable, info, convert);
    }

    @Override
    public void addStringArray(Variable output, Variable input) {
        method.assignWithInvoke(output, "SignatureTool.add_string_array", output, input);
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
    public void onAddReference(VariableType variableType) {
        parent.addReference(xStringArray.inst.getType());
    }

    @Override
    public void startMethod(VariableType returnType, String methodDescriptor, VariableList params) {
        method = new Python3Method(parent);
        parent.addReference(SignatureTool.getType());
        method.startStaticMethod(null, methodDescriptor, params);
    }

    @Override
    public void endMethod() {
        method.endMethod();
    }

}
