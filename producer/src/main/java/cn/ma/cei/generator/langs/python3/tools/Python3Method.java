/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.python3.tools;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.langs.python3.Python3Code;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author U0151316
 */
public class Python3Method {

    private Python3Code code = new Python3Code();
    private Python3Class parent;

    public Python3Method(Python3Class parent) {
        this.parent = parent;
    }

    public String defineVariable(Variable variable) {
        return variable.getDescriptor();
    }

    public String useVariable(Variable variable) {
        return variable.getDescriptor();
    }

    public String newInstance(VariableType type, Variable... params) {
        parent.addReference(type);
        List<Variable> tmp = Arrays.asList(params);
        return type.getDescriptor() + "(" + invokeParamString(tmp) + ")";
    }

    public void addReturn(Variable variable) {
        code.appendWordsln("return", variable.getDescriptor());
    }

    public void startFor(Variable item, String statement) {
        code.appendWordsln("for", item.getDescriptor(), "in", statement);
        code.startBlock();
    }

    public void endFor() {
        code.endBlock();
    }

    public void startIf(String statement) {
        code.appendWordsln("if", statement + ":");
        code.startBlock();
    }

    public void endIf() {
        code.endBlock();
    }

    public void addAssign(String left, String right) {
        code.appendWordsln(left, "=", right);
    }

    public void addInvoke(String method, Variable... params) {
        code.appendWordsln(invoke(method, params));
    }

    public String invoke(String method, Variable... params) {
        List<Variable> tmp = Arrays.asList(params);
        return method + "(" + invokeParamString(tmp) + ")";
    }

    public void startMethod(VariableType returnType, String methodName, List<Variable> params) {
        String paramString = defineParamString(params);
        if (paramString.isEmpty()) {
            code.appendWordsln("def", methodName + "(self):");
        } else {
            code.appendWordsln("def", methodName + "(self, " + defineParamString(params) + "):");
        }
        code.startBlock();
    }

    public void startStaticMethod(VariableType returnType, String methodName, List<Variable> params) {
        code.appendln("@staticmethod");
        String paramString = defineParamString(params);
        if (paramString.isEmpty()) {
            code.appendWordsln("def", methodName + "():");
        } else {
            code.appendWordsln("def", methodName + "(" + defineParamString(params) + "):");
        }
        code.startBlock();
    }

    public Python3Code getCode() {
        return code;
    }

    public void endMethod() {
        code.endBlock();
    }

    private String invokeParamString(List<Variable> params) {
        if (params == null || params.isEmpty()) {
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
        if (params == null || params.isEmpty()) {
            return "";
        }
        String paramString = "";
        for (Variable variable : params) {
            if (paramString.equals("")) {
                paramString += variable.getDescriptor();
            } else {
                paramString += ", " + variable.getDescriptor();
            }
        }
        return paramString;
    }
}
