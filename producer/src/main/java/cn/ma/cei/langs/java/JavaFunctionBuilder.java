/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.java;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IMethodBuilder;
import cn.ma.cei.generator.buildin.CEIUtils;
import cn.ma.cei.langs.java.tools.JavaClass;
import cn.ma.cei.langs.java.tools.JavaMethod;

import java.util.List;

/**
 * @author u0151316
 */
public class JavaFunctionBuilder implements IMethodBuilder {

    private JavaMethod method;
    private final JavaClass parent;

    public JavaFunctionBuilder(JavaClass javaClass) {
        parent = javaClass;
    }

    @Override
    public void onAddReference(VariableType variableType) {
        parent.addReference(variableType);
    }


    @Override
    public void startMethod(VariableType returnType, String methodDescriptor, List<Variable> params) {
        method = new JavaMethod(parent);
        parent.addReference(CEIUtils.getType());
        method.startStaticMethod(returnType, methodDescriptor, params);
    }

    @Override
    public IDataProcessorBuilder createDataProcessorBuilder() {
        return new JavaDataProcessorBuilder(method);
    }

    @Override
    public void endMethod(Variable returnVariable) {
        if (returnVariable != null) {
            method.addReturn(returnVariable.getDescriptor());
        }
        method.endMethod();
        parent.addMethod(method);
    }
}
