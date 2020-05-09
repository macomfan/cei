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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author U0151316
 */
public class GoMethod extends GoVarMgr {

    private static final String returnIndicator = "data";
    private final GoCode code = new GoCode();
    private GoStruct parent = null;
    private boolean returnError;
    private String methodName;
    private GoStruct inputStruct = null;

    public GoMethod(GoStruct parent) {
        this.parent = parent;
    }

    public String getMethodName() {
        return methodName;
    }

    public GoCode getCode() {
        return code;
    }

    public GoStruct getInputStruct() {
        return inputStruct;
    }

    public String newInstance(VariableType type) {
        return "new(" + type.getDescriptor() + ")";
    }

    public String newPointerInstance(VariableType type) {
        return "new(*" + type.getDescriptor() + ")";
    }

    public String createInstance(VariableType type) {
        return type.getDescriptor() + "{}";
    }

    public String useVariable(GoVar variable) {
        return queryVariableDescriptor(variable);
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

    public void addReference(VariableType type) {
        parent.addReference(type);
    }

    public void addReference(String type) {
        parent.addReference(type);
    }

    public String invoke(String method, GoVar... params) {
        List<GoVar> tmp = Arrays.asList(params);
        return method + "(" + invokeParamString(tmp) + ")";
    }

//    public void startInterface(GoType returnType, String methodName, List<GoVar> params) {
//        if (!Checker.isNull(params) && params.size() > 1) {
//            List<GoVar> vars = mergeInputVar(params);
//            inputStruct = new GoStruct("Args" + methodName);
//            vars.forEach(item -> inputStruct.addPublicMember(item));
//        }
//        startMethod(returnType, methodName, params);
//    }

    public void startInterface(GoType returnType, boolean returnError, String methodName, List<GoVar> mergeParams, List<GoVar> normalParams) {
        if (!Checker.isNull(mergeParams) && mergeParams.size() > 1) {
            List<GoVar> vars = mergeInputVar(mergeParams);
            inputStruct = new GoStruct("Args" + methodName);
            vars.forEach(item -> inputStruct.addPublicMember(item));

        }
        startMethod(returnType, returnError, methodName, normalParams);
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
        // commonTypeString to collect all type of params, if they are same type, merge them
        // e.g.  merged: func (p1, p2, p3, string)  un-merged: func (p1 string, p2 string, p3 string)
        Set<String> commonTypeString = new HashSet<>();
        if (inputStruct != null) {
            paramString += "args " + inputStruct.getStructName();
            commonTypeString.add(inputStruct.getStructName());
        }
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
