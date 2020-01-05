/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.golang;

import cn.ma.cei.generator.builder.SignatureBuilder;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableList;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.langs.golang.tools.GoMethod;
import cn.ma.cei.generator.langs.golang.tools.GoStruct;

/**
 *
 * @author U0151316
 */
public class GoSignatureBuilder extends SignatureBuilder {

    private GoStruct signatureStruct;
    private GoMethod method;

    public GoSignatureBuilder(GoStruct signatureStruct) {
        this.signatureStruct = signatureStruct;
    }

    @Override
    public void newStringArray(Variable stringArray) {
        method.addAssignAndDeclare(method.useVariable(stringArray), "make([]string, 10)");
    }

    @Override
    public void getNow(Variable output, Variable format) {
        method.addAssignAndDeclare(method.useVariable(output), method.invoke("signature.GetNow", format));
    }

    @Override
    public void addQueryString(Variable requestVariable, Variable key, Variable value) {
        method.addInvoke(requestVariable.nameDescriptor + ".AddQueryString", key, value);
    }

    @Override
    public void appendToString(boolean needDefineNewOutput, Variable output, Variable input) {
        if (needDefineNewOutput) {
            method.addAssignAndDeclare(method.useVariable(output), "make([]string, 10)");
        } else {
            method.addAssign(method.useVariable(output), method.invoke("SignatureTool.appendToString", output, input));
        }
    }

    @Override
    public void combineQueryString(Variable requestVariable, Variable output, Variable sort, Variable separator) {
        method.addAssignAndDeclare(method.useVariable(output), method.invoke("SignatureTool.combineQueryString", requestVariable, sort, separator));
    }

    @Override
    public void getRequestInfo(Variable requestVariable, Variable output, Variable info, Variable convert) {
        method.addAssignAndDeclare(method.useVariable(output), method.invoke("signature.GetRequestInfo", requestVariable, info, convert));
    }

    @Override
    public void addStringArray(Variable output, Variable input) {
        method.addAssign(method.useVariable(output), method.invoke("signature.addStringArray", output, input));
    }

    @Override
    public void combineStringArray(Variable output, Variable input, Variable separator) {
         method.addAssignAndDeclare(method.useVariable(output), method.invoke("signature.CombineStringArray", input, separator));
    }

    @Override
    public void base64(Variable output, Variable input) {
        method.addAssignAndDeclare(method.useVariable(output), method.invoke("signature.Base64", input));
    }

    @Override
    public void hmacsha265(Variable output, Variable input, Variable key) {
        method.addAssignAndDeclare(method.useVariable(output), method.invoke("signature,hmacsha256", input, key));
    }

    @Override
    public void onAddReference(VariableType variableType) {
        signatureStruct.addReference(variableType);
    }

    @Override
    public void startMethod(VariableType returnType, String methodDescriptor, VariableList params) {
        method = new GoMethod(signatureStruct);
        method.startMethod(returnType, methodDescriptor, params);
    }

    @Override
    public void endMethod() {
        method.endMethod();
    }

}
