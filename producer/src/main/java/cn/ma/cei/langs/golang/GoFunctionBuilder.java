/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.golang;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IMethodBuilder;
import cn.ma.cei.langs.golang.tools.GoFile;
import cn.ma.cei.langs.golang.tools.GoMethod;
import cn.ma.cei.langs.golang.vars.GoType;
import cn.ma.cei.langs.golang.vars.GoVar;
import cn.ma.cei.utils.WordSplitter;

import java.util.LinkedList;
import java.util.List;

/**
 * @author U0151316
 */
public class GoFunctionBuilder implements IMethodBuilder {

    private final GoFile mainFile;
    private GoMethod method;

    public GoFunctionBuilder(GoFile mainFile) {
        this.mainFile = mainFile;
    }

//    @Override
//    public void newStringArray(Variable stringArray) {
//        method.addAssignAndDeclare(method.useVariable(new GoVar(stringArray)), "make([]string, 10)");
//    }
//
//
//
//    @Override
//    public void appendToString(boolean needDefineNewOutput, Variable output, Variable input) {
//        if (needDefineNewOutput) {
//            method.addAssignAndDeclare(method.useVariable(new GoVar(output)), "make([]string, 10)");
//        } else {
//            method.addAssign(method.useVariable(new GoVar(output)), method.invoke("authentication.AppendToString", new GoVar(output), new GoVar(input)));
//        }
//    }
//
//
//
//    @Override
//    public void addStringArray(Variable output, Variable input) {
//        method.addAssign(method.useVariable(new GoVar(output)),
//                method.invoke("authentication.addStringArray", new GoVar(output), new GoVar(input)));
//    }
//
//    @Override
//    public void combineStringArray(Variable output, Variable input, Variable separator) {
//        method.addAssignAndDeclare(method.useVariable(new GoVar(output)),
//                method.invoke("authentication.CombineStringArray", new GoVar(input), new GoVar(separator)));
//    }

    @Override
    public void onAddReference(VariableType variableType) {
        method.addReference(variableType);
    }

    @Override
    public void startMethod(VariableType returnType, String methodDescriptor, List<Variable> params) {
        method = new GoMethod(null);
        List<GoVar> tmp = new LinkedList<>();
        params.forEach(item -> {
            tmp.add(method.varPtr(item));
        });
        GoType type = null;
        if (returnType != null) {
            type = new GoType(returnType);
        }
        method.startMethod(type, false, WordSplitter.getLowerCamelCase(methodDescriptor), tmp);
    }

    @Override
    public IDataProcessorBuilder createDataProcessorBuilder() {
        return new GoDataProcessorBuilder(method);
    }

    @Override
    public void endMethod(Variable returnVariable) {
        if (returnVariable != null) {
            method.addReturn(method.var(returnVariable));
        }
        method.endMethod();
        mainFile.addMethod(method);
    }
}
