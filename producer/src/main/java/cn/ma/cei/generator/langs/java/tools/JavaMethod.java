/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.java.tools;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.langs.java.JavaCode;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author u0151316
 */
public class JavaMethod {

    private JavaCode code = new JavaCode();
    private JavaClass parent;

    public JavaMethod(JavaClass parent) {
        this.parent = parent;
    }

    public JavaCode getCode() {
        return code;
    }

    public String defineVariable(Variable variable) {
        parent.addReference(variable.getType());
        return variable.getTypeDescriptor() + " " + variable.getDescriptor();
    }

    public String useVariable(Variable variable) {
        return variable.getDescriptor();
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

    public String invoke(String method, Variable... params) {
        List<Variable> tmp = Arrays.asList(params);
        return method + "(" + invokeParamString(tmp) + ")";
    }

    public void startMethod(VariableType returnType, String methodName, List<Variable> params) {
        if (returnType == null) {
            code.appendWordsln("public", "void", methodName + "(" + defineParamString(params) + ")", "{");
        } else {
            parent.addReference(returnType);
            code.appendWordsln("public", returnType.getDescriptor(), methodName + "(" + defineParamString(params) + ")", "{");
        }
        code.startBlock();
    }

    public void startStaticMethod(VariableType returnType, String methodName, List<Variable> params) {
        if (returnType == null) {
            code.appendWordsln("public", "static", "void", methodName + "(" + defineParamString(params) + ")", "{");
        } else {
            parent.addReference(returnType);
            code.appendWordsln("public", "static", returnType.getDescriptor(), methodName + "(" + defineParamString(params) + ")", "{");
        }
        code.startBlock();
    }

    public void endMethod() {
        code.endBlock();
        code.appendln("}");
    }

    private String invokeParamString(List<Variable> params) {
        if (params.isEmpty()) {
            return "";
        }
        String paramString = "";
        for (Variable p : params) {
            if (p == null) {
                continue;
            }
            if (paramString.equals("")) {
                paramString += p.getDescriptor();
            } else {
                paramString += ", " + p.getDescriptor();
            }
        }
        return paramString;
    }

    private String defineParamString(List<Variable> params) {
        if (params.isEmpty()) {
            return "";
        }
        String paramString = "";
        for (Variable variable : params) {
            parent.addReference(variable.getType());
            if (paramString.equals("")) {
                paramString += variable.getTypeDescriptor() + " " + variable.getDescriptor();
            } else {
                paramString += ", " + variable.getTypeDescriptor() + " " + variable.getDescriptor();
            }
        }
        return paramString;
    }
}
