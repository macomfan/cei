/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.python3.tools;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableList;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.langs.python3.Python3Code;

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

    public void startMethod(VariableType returnType, String methodName, VariableList params) {
        if (params.isEmpty()) {
            code.appendWordsln("def", methodName + "(self, " + defineParamString(params) + "):");
        } else {
            code.appendWordsln("def", methodName + "(self):");
        }
        
        code.startBlock();
    }

    public void startStaticMethod(VariableType returnType, String methodName, VariableList params) {
        code.appendln("@staticmethod");
        startMethod(returnType, methodName, params);
    }

    public void newInstance(Variable variable) {
        code.appendWordsln(variable.nameDescriptor, "=", variable.type.getDescriptor() + "()");
    }

    public void invoke(String methodName, Variable... params) {
        String paramsString = invokeParamString(params);
        code.appendWordsln(methodName + "(" + paramsString + ")");
    }

    public void newInstanceWithInvoke(Variable variable, String methodName, Variable... params) {
        String paramString = invokeParamString(params);
        code.appendWordsln(variable.nameDescriptor, "=", methodName + "(" + paramString + ")");
    }

    public void assign(Variable variable, Variable value) {
        code.appendWordsln(variable.nameDescriptor, "=", value.nameDescriptor);
    }

    public void newInstanceWithValue(Variable variable, Variable value) {
        code.appendWordsln(variable.nameDescriptor, "=", value.nameDescriptor);
    }

    public void startFor(Variable item, String loopStatement) {
        code.appendWordsln("for", item.nameDescriptor, "in", loopStatement);
        code.startBlock();
    }

    public void endFor() {
        code.endBlock();
    }

    public void returnVariable(Variable variable) {
        code.appendWords("return", variable.nameDescriptor);
    }

    public Python3Code getCode() {
        return code;
    }

    public void endMethod() {
        code.endBlock();
        code.endln();
    }

    public void assignWithInvoke(Variable variable, String methodName, Variable... params) {
        String paramString = invokeParamString(params);
        code.appendWordsln(variable.nameDescriptor, "=", methodName + "(" + paramString + ")");
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
                paramString += variable.nameDescriptor;
            } else {
                paramString += ", " + variable.nameDescriptor;
            }
        }
        return paramString;
    }
}
