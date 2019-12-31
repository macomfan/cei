/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.java.tools;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableList;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.langs.java.JavaCode;

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
        return variable.type.getDescriptor() + " " + variable.nameDescriptor;
    }

    public String useVariable(Variable variable) {
        return variable.nameDescriptor;
    }

    public void addReturn(Variable variable) {
        code.appendJavaLine("return", variable.nameDescriptor);
    }

    public void startFor(Variable item, String statement) {
        code.appendWordsln(statement + ".forEach(" + item.nameDescriptor + " -> {");
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
        return "new " + type.getDescriptor() + "(" + invokeParamString(params) + ")";
    }

    public void addAssign(String left, String right) {
        code.appendJavaLine(left, "=", right);
    }

    public void addInvoke(String method, Variable... params) {
        code.appendJavaLine(invoke(method, params));
    }

    public String invoke(String method, Variable... params) {
        return method + "(" + invokeParamString(params) + ")";
    }

    public void startMethod(VariableType returnType, String methodName, VariableList params) {
        if (returnType == null) {
            code.appendWordsln("public", "void", methodName + "(" + defineParamString(params) + ")", "{");
        } else {
            code.appendWordsln("public", returnType.getDescriptor(), methodName + "(" + defineParamString(params) + ")", "{");
        }
        code.startBlock();
    }

    public void startStaticMethod(VariableType returnType, String methodName, VariableList params) {
        if (returnType == null) {
            code.appendWordsln("public", "static", "void", methodName + "(" + defineParamString(params) + ")", "{");
        } else {
            code.appendWordsln("public", "static", returnType.getDescriptor(), methodName + "(" + defineParamString(params) + ")", "{");
        }
        code.startBlock();
    }

    public void endMethod() {
        code.endBlock();
        code.appendln("}");
    }

    private String invokeParamString(Variable... params) {
        if (params == null) {
            return "";
        }
        String paramString = "";
        for (Variable p : params) {
            if (p == null) {
                continue;
            }
            if (paramString.equals("")) {
                paramString += p.nameDescriptor;
            } else {
                paramString += ", " + p.nameDescriptor;
            }
        }
        return paramString;
    }

    private String defineParamString(VariableList params) {
        if (params == null) {
            return "";
        }
        String paramString = "";
        for (Variable variable : params.getVariableList()) {
            if (paramString.equals("")) {
                paramString += variable.type.getDescriptor() + " " + variable.nameDescriptor;
            } else {
                paramString += ", " + variable.type.getDescriptor() + " " + variable.nameDescriptor;
            }
        }
        return paramString;
    }
}
