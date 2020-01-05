/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.golang.tools;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableList;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.langs.golang.GoCode;

/**
 *
 * @author U0151316
 */
public class GoMethod {

    private GoStruct parent = null;
    private GoCode code = new GoCode();
    private String methodName;

    public GoMethod(GoStruct parent) {
        this.parent = parent;
    }

    public String getMethodName() {
        return methodName;
    }
    
    public GoCode getCode() {
        return code;
    }

    public String newInstacneByConstructor(String packageName, VariableType type, Variable... params) {
        return packageName + ".New" + type.getDescriptor() + "(" + invokeParamString(params) + ")";
    }

    public String newInstance(VariableType type) {
        return "new(" + type.getDescriptor() + ")";
    }

    public String defineVariable(Variable variable) {
        //parent.addReference(variable.type);
        return "var " + variable.nameDescriptor + " " + variable.type.getDescriptor();
    }

    public String useVariable(Variable variable) {
        return variable.nameDescriptor;
    }

    public void addReturn(Variable variable) {
        code.appendWordsln("return", variable.nameDescriptor);
    }

    public void addAssign(String left, String right) {
        code.appendWordsln(left, "=", right);
    }

    public void addAssignAndDeclare(String left, String right) {
        code.appendWordsln(left, ":=", right);
    }

    public void addInvoke(String method, Variable... params) {
        code.appendln(invoke(method, params));
    }

    public String invoke(String method, Variable... params) {
        return method + "(" + invokeParamString(params) + ")";
    }

    public void startMethod(VariableType returnType, String methodName, VariableList params) {
        this.methodName = methodName;
        String returnString = "";
        if (returnType != null) {
            returnString += "*";
            returnString += returnType.getDescriptor();
            returnString += " ";
        }
        if (parent != null) {
            code.appendWordsln("func", methodBelongTo(), methodName + "(" + defineParamString(params) + ")", returnString + "{");
        } else {
            code.appendWordsln("func", methodName + "(" + defineParamString(params) + ")", returnString + "{");
        }
        code.startBlock();
    }

    public void endMethod() {
        code.endBlock();
        code.appendln("}");
    }

    private String methodBelongTo() {
        if (parent == null) {
            return "";
        }
        return "(this " + "*" + parent.getStructName() + ")";
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
            //parent.addReference(variable.type);
            if (paramString.equals("")) {
                paramString += variable.nameDescriptor + " " + variable.type.getDescriptor();
            } else {
                paramString += ", " + variable.nameDescriptor + " " + variable.type.getDescriptor();
            }
        }
        return paramString;
    }
}
