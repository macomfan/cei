/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.golang.tools;

import cn.ma.cei.generator.langs.golang.GoCode;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public String newInstance(GoType type) {
        return "new(" + type.getDescriptor() + ")";
    }

    public String defineVariable(GoVar variable) {
        //parent.addReference(variable.type);
        return "var " + variable.getNameDescriptor() + " " + variable.getTypeDescriptor();
    }

    public String useVariable(GoVar variable) {
        return variable.getNameDescriptor();
    }

    public void addReturn(GoVar variable) {
        code.appendWordsln("return", variable.getNameDescriptor());
    }

    public void addAssign(String left, String right) {
        code.appendWordsln(left, "=", right);
    }

    public void addAssignAndDeclare(String left, String right) {
        code.appendWordsln(left, ":=", right);
    }

    public void addInvoke(String method, GoVar... params) {
        code.appendln(invoke(method, params));
    }

    public String invoke(String method, GoVar... params) {
        List<GoVar> tmp = Arrays.asList(params);
        return method + "(" + invokeParamString(tmp) + ")";
    }

    public void startMethod(GoType returnType, String methodName, List<GoVar> params) {
        this.methodName = methodName;
        String returnString = "";
        if (returnType != null) {
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

    public void startFor(GoVar item, String statement) {
        code.appendWordsln("for _, " + item.getNameDescriptor() + " := range " + statement, "{");
        code.startBlock();
    }
    
    public void endFor() {
        code.endBlock();
        code.appendln("}");
    }

    private String methodBelongTo() {
        if (parent == null) {
            return "";
        }
        return "(inst " + "*" + parent.getStructName() + ")";
    }

    private String invokeParamString(List<GoVar> params) {
        if (params == null) {
            return "";
        }
        String paramString = "";
        for (GoVar p : params) {
            if (p == null) {
                continue;
            }
            if (paramString.equals("")) {
                paramString += p.getNameDescriptor();
            } else {
                paramString += ", " + p.getNameDescriptor();
            }
        }
        return paramString;
    }

    private String defineParamString(List<GoVar> params) {
        if (params == null) {
            return "";
        }
        String paramString = "";
        Set<String> commonTypeString = new HashSet<>();
        params.forEach((param) -> {
            commonTypeString.add(param.getTypeDescriptor());
        });
        if (commonTypeString.size() == 1) {
            for (GoVar param : params) {
                if (paramString.equals("")) {
                    paramString += param.getNameDescriptor();
                } else {
                    paramString += ", " + param.getNameDescriptor();
                }
            }
            paramString += " " + params.get(0).getTypeDescriptor();
        } else {
            for (GoVar param : params) {
                if (paramString.equals("")) {
                    paramString += param.getNameDescriptor() + " " + param.getTypeDescriptor();
                } else {
                    paramString += ", " + param.getNameDescriptor() + " " + param.getTypeDescriptor();
                }
            }
        }
        return paramString;
    }
}
