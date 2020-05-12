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

    @Override
    public void onAddReference(VariableType variableType) {
        method.addReference(variableType);
    }

    @Override
    public void startMethod(VariableType returnType, String methodDescriptor, List<Variable> params) {
        method = new GoMethod(null);
        GoType type = null;
        if (returnType != null) {
            type = new GoType(returnType);
        }
        method.startMethod(type, false, WordSplitter.getLowerCamelCase(methodDescriptor), method.varListToList(params));
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
