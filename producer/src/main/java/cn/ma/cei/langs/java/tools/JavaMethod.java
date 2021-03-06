/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.java.tools;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.langs.java.JavaCode;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author u0151316
 */
public class JavaMethod {

    private final JavaCode code = new JavaCode();
    private final JavaClass parent;
    private String methodName = "";
    private boolean isStatic = false;

    public JavaMethod(JavaClass parent) {
        this.parent = parent;
    }

    public JavaMethod(JavaClass parent, String methodName) {
        this.parent = parent;
        this.methodName = methodName;
    }

    public JavaCode getCode() {
        return code;
    }

    public String getMethodName() {
        return methodName;
    }

    public String defineVariable(Variable variable) {
        parent.addReference(variable.getType());
        return variable.getTypeDescriptor() + " " + variable.getDescriptor();
    }

    public String useVariable(Variable variable) {
        return variable.getDescriptor();
    }

    public void addCode(JavaCode code) {
        this.code.appendCode(code);
    }

    public void addReturn(Variable variable) {
        code.appendJavaLine("return", variable.getDescriptor());
    }

    public void addReturn(String statement) {
        code.appendJavaLine("return", statement);
    }

    public void startFor(Variable item, String statement) {
        code.appendWordsln(statement + ".forEach(" + item.getDescriptor() + " -> {");
        code.startBlock();
    }

    public void startIf(String statement) {
        code.appendWordsln("if (" + statement + ") {");
        code.startBlock();
    }
    
    public void endIf() {
        code.endBlock();
        code.appendln("}");
    }

    public void endFor() {
        code.endBlock();
        code.appendln("});");
    }

    public void addReference(VariableType type) {
        parent.addReference(type);
    }

    public void addReference(String type) {
        parent.addReference(type);
    }

    public String newInstance(VariableType type, Variable... params) {
        parent.addReference(type);
        List<Variable> tmp = Arrays.asList(params);
        return "new " + type.getDescriptor() + "(" + invokeParamString(tmp) + ")";
    }

    public void addAssign(String left, String right) {
        code.appendJavaLine(left, "=", right);
    }

    public void addInvoke(String method, Variable... params) {
        code.appendJavaLine(invoke(method, params));
    }

    public void addLambda(Variable variable, String methodName, List<Variable> params) {
        code.appendln(variable.getDescriptor() + "." + methodName + "((" + invokeParamString(params) + ") -> {");
        code.startBlock();
    }

    public void addLambda(String methodName, List<Variable> params) {
        code.appendln(methodName + "((" + invokeParamString(params) + ") -> {");
        code.startBlock();
    }

    public void endLambda() {
        code.endBlock();
        code.appendln("});");
    }

    public String invoke(String method, Variable... params) {
        List<Variable> tmp = Arrays.asList(params);
        return method + "(" + invokeParamString(tmp) + ")";
    }

    public void startConstructor(String params) {
        code.appendWordsln("public", parent.getClassName() + "(" + params + ")", "{");
        code.startBlock();
    }

    public void startMethod(VariableType returnType, String methodName, List<Variable> params) {
        this.methodName = methodName;
        String staticString = null;
        if (isStatic) {
            staticString = "static";
        }
        if (returnType == null) {
            code.appendWordsln("public", staticString, "void", methodName + "(" + defineParamString(params) + ")", "{");
        } else {
            parent.addReference(returnType);
            code.appendWordsln("public", staticString, returnType.getDescriptor(), methodName + "(" + defineParamString(params) + ")", "{");
        }
        code.startBlock();
    }

    public void startStaticMethod(VariableType returnType, String methodName, List<Variable> params) {
        this.isStatic = true;
        this.startMethod(returnType, methodName, params);
    }

    public void endMethod() {
        code.endBlock();
        code.appendln("}");
    }

    private String invokeParamString(List<Variable> params) {
        if (params.isEmpty()) {
            return "";
        }
        StringBuilder paramString = new StringBuilder();
        for (Variable p : params) {
            if (p == null) {
                continue;
            }
            if (paramString.toString().equals("")) {
                paramString.append(p.getDescriptor());
            } else {
                paramString.append(", ").append(p.getDescriptor());
            }
        }
        return paramString.toString();
    }

    private String defineParamString(List<Variable> params) {
        if (params.isEmpty()) {
            return "";
        }
        StringBuilder paramString = new StringBuilder();
        for (Variable variable : params) {
            parent.addReference(variable.getType());
            if (paramString.toString().equals("")) {
                paramString.append(variable.getTypeDescriptor()).append(" ").append(variable.getDescriptor());
            } else {
                paramString.append(", ").append(variable.getTypeDescriptor()).append(" ").append(variable.getDescriptor());
            }
        }
        return paramString.toString();
    }
}
