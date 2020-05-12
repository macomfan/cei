/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.golang.tools;

import cn.ma.cei.generator.VariableType;
import cn.ma.cei.langs.golang.GoCode;
import cn.ma.cei.langs.golang.vars.GoType;
import cn.ma.cei.langs.golang.vars.GoVar;
import cn.ma.cei.utils.Checker;

import java.util.*;

/**
 * @author U0151316
 */
public class GoMethod extends GoVarMgr {

    private static final String returnIndicator = "data";
    private final GoCode code = new GoCode();
    private final GoStruct parent;
    private boolean returnError;
    private String methodName;
    private final Set<String> importList = new HashSet<>();

    public GoMethod(GoStruct parent) {
        if (parent == null) {
            int a = 0;
        }
        this.parent = parent;
    }

    public String getMethodName() {
        return methodName;
    }

    public GoCode getCode() {
        return code;
    }

//    public String newInstance(VariableType type) {
//        return "new(" + type.getDescriptor() + ")";
//    }

    public String createInstance(VariableType type) {
        return type.getDescriptor() + "{}";
    }

    public String useVariable(GoVar variable) {
        return variable.getDescriptor();
    }

    public void addReturn(GoVar variable) {
        if (returnError) {
            code.appendWordsln("return", variable.getDescriptor() + ",", "nil");
        } else {
            code.appendWordsln("return", variable.getDescriptor());
        }
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

    public Set<String> getImportList() {
        return importList;
    }

    public void addReference(VariableType type) {
        importList.addAll(type.getReferences());
    }

    public void addReference(String type) {
        importList.add(type);
    }

    public String invoke(String method, GoVar... params) {
        List<GoVar> tmp = Arrays.asList(params);
        return method + "(" + invokeParamString(tmp) + ")";
    }

    public void addLambda(GoVar variable, String methodName, List<GoVar> params, GoType returnType) {
        String returnString = " ";
        if (returnType != null) {
            returnString = " " + returnType.getDescriptor();
        }
        code.appendln(variable.getDescriptor() + "." + methodName + "(func(" + defineParamString(params) + ")" + returnString + " {");
        code.startBlock();
    }

    public void endLambda() {
        code.endBlock();
        code.appendln("})");
    }

    public void startMethod(GoType returnType, boolean returnError, String methodName, List<GoVar> params) {
        this.returnError = returnError;
        this.methodName = methodName;
        String returnString = "";
        if (returnType != null) {
            returnString += returnType.getDescriptor();
        }
        if (returnError) {
            if (!"".equals(returnString)) {
                returnString = returnIndicator + " " + returnString;
                returnString += " , exception error";
                returnString = "(" + returnString + ")";
            } else {
                returnString = "error ";
            }
        }
        if (parent != null) {
            code.appendWordsln("func", methodBelongTo(), methodName + "(" + defineParamString(params) + ")", returnString, "{");
        } else {
            code.appendWordsln("func", methodName + "(" + defineParamString(params) + ")", returnString, "{");
        }
        code.startBlock();
        if (returnError) {
            addReference("errors");
            code.appendWordsln("defer", "func()", "{");
            code.newBlock(() -> {
                code.appendln("if err := recover(); err != nil {");
                code.newBlock(() -> {
                    code.appendln("exception = errors.New(err.(string))");
                });
                code.appendln("}");
            });
            code.appendln("}()");
        }
    }

    public void endMethod() {
        code.endBlock();
        code.appendln("}");
    }

    public void startFor(GoVar item, String statement) {
        code.appendWordsln("for _, " + item.getDescriptor() + " := range " + statement, "{");
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
                paramString += p.getDescriptor();
            } else {
                paramString += ", " + p.getDescriptor();
            }
        }
        return paramString;
    }

    private String defineParamString(List<GoVar> params) {
        String paramString = "";
        Set<String> commonTypeString = new HashSet<>();
        if (Checker.isNull(params)) {
            return paramString;
        }
        params.forEach((param) -> {
            commonTypeString.add(param.getTypeDescriptor());
        });
        if (commonTypeString.size() == 1) {
            for (GoVar param : params) {
                if (paramString.equals("")) {
                    paramString += param.getDescriptor();
                } else {
                    paramString += ", " + param.getDescriptor();
                }
            }
            paramString += " " + params.get(0).getTypeDescriptor();
        } else {
            for (GoVar param : params) {
                if (paramString.equals("")) {
                    paramString += param.getDescriptor() + " " + param.getTypeDescriptor();
                } else {
                    paramString += ", " + param.getDescriptor() + " " + param.getTypeDescriptor();
                }
            }
        }
        return paramString;
    }
}
