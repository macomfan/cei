/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.python3;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IMethodBuilder;
import cn.ma.cei.generator.buildin.CEIUtils;
import cn.ma.cei.langs.python3.tools.Python3Class;
import cn.ma.cei.langs.python3.tools.Python3Method;
import cn.ma.cei.model.types.xStringArray;

import java.util.List;

/**
 * @author u0151316
 */
public class Python3FunctionBuilder implements IMethodBuilder {

    private Python3Method method;
    private final Python3Class parent;

    public Python3FunctionBuilder(Python3Class parent) {
        this.parent = parent;
    }

    @Override
    public void onAddReference(VariableType variableType) {
        parent.addReference(variableType);
    }

    @Override
    public void startMethod(VariableType returnType, String methodDescriptor, List<Variable> params) {
        method = new Python3Method(parent);
        parent.addReference(CEIUtils.getType());
        method.startStaticMethod(null, methodDescriptor, params);
    }

    @Override
    public IDataProcessorBuilder createDataProcessorBuilder() {
        return new Python3DataProcessorBuilder(method);
    }

    @Override
    public void endMethod(Variable returnVariable) {
        if (returnVariable != null) {
            method.addReturn(returnVariable);
        }
        method.endMethod();
        parent.addMethod(method);
    }
}
