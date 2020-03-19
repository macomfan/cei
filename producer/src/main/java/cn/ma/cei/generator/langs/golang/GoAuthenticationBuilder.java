/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.golang;

import cn.ma.cei.generator.builder.*;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.langs.golang.tools.*;
import cn.ma.cei.utils.WordSplitter;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author U0151316
 */
public class GoAuthenticationBuilder implements IAuthenticationBuilder {

    private GoFile mainFile;
    private GoMethod method;

    public GoAuthenticationBuilder(GoFile mainFile) {
        this.mainFile = mainFile;
    }

    @Override
    public void newStringArray(Variable stringArray) {
        method.addAssignAndDeclare(method.useVariable(new GoVar(stringArray)), "make([]string, 10)");
    }

    @Override
    public void addQueryString(Variable requestVariable, Variable key, Variable value) {
        method.addInvoke(requestVariable.getDescriptor() + ".AddQueryString", new GoVar(key), new GoVar(value));
    }

    @Override
    public void appendToString(boolean needDefineNewOutput, Variable output, Variable input) {
        if (needDefineNewOutput) {
            method.addAssignAndDeclare(method.useVariable(new GoVar(output)), "make([]string, 10)");
        } else {
            method.addAssign(method.useVariable(new GoVar(output)), method.invoke("authentication.AppendToString", new GoVar(output), new GoVar(input)));
        }
    }

    @Override
    public void combineQueryString(Variable requestVariable, Variable output, Variable sort, Variable separator) {
        method.addAssignAndDeclare(method.useVariable(new GoVar(output)),
                method.invoke("authentication.CombineQueryString", new GoVar(requestVariable), new GoVar(sort), new GoVar(separator)));
    }

    @Override
    public void getRequestInfo(Variable requestVariable, Variable output, Variable info, Variable convert) {
        method.addAssignAndDeclare(method.useVariable(new GoVar(output)),
                method.invoke("authentication.GetRequestInfo", new GoVar(requestVariable), new GoVar(info), new GoVar(convert)));
    }

    @Override
    public void addStringArray(Variable output, Variable input) {
        method.addAssign(method.useVariable(new GoVar(output)),
                method.invoke("authentication.addStringArray", new GoVar(output), new GoVar(input)));
    }

    @Override
    public void combineStringArray(Variable output, Variable input, Variable separator) {
        method.addAssignAndDeclare(method.useVariable(new GoVar(output)),
                method.invoke("authentication.CombineStringArray", new GoVar(input), new GoVar(separator)));
    }

    @Override
    public void onAddReference(VariableType variableType) {

    }

    @Override
    public void startMethod(VariableType returnType, String methodDescriptor, List<Variable> params) {
        method = new GoMethod(null);
        List<GoVar> tmp = new LinkedList<>();
        params.forEach(item -> {
            tmp.add(new GoPtrVar(item));
        });
        method.startMethod(null, WordSplitter.getLowerCamelCase(methodDescriptor), tmp);
    }

    @Override
    public IDataProcessorBuilder createDataProcessorBuilder() {
        return null;
    }

    @Override
    public void endMethod() {
        method.endMethod();
        mainFile.addMethod(method);
    }

}
