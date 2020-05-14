package cn.ma.cei.langs.cpp.tools;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;

import java.util.Arrays;
import java.util.List;

public class CppMethod {

    private final CodeForHpp codeH = new CodeForHpp();
    private final CodeForCpp codeCpp = new CodeForCpp();
    private final CppClass parent;

    public CppMethod(CppClass parent) {
        this.parent = parent;
    }

    public CodeForCpp getCode() {
        return codeCpp;
    }

    public CodeForHpp getCodeForH() {
        return codeH;
    }

    public void addReference(VariableType type) {

    }

    public void startMethod(VariableType returnType, String methodName, List<Variable> params) {
        codeH.appendCppWordsln(returnType.getDescriptor(), methodName + "(" + defineParamString(params) + ")");

        codeCpp.appendWordsln(returnType.getDescriptor(), parent.getClassName() + "::" + methodName + "(" + defineParamString(params) + ") {");
        codeCpp.startBlock();
    }

    public void endMethod() {
        codeCpp.endBlock();
        codeCpp.appendln("}");
    }

    private String defineParamString(List<Variable> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        String paramString = "";
        boolean isFirst = true;
        for (Variable variable : params) {
            if (isFirst) {
                paramString += "const " + variable.getTypeDescriptor() + "& " + variable.getDescriptor();
                isFirst = false;
            } else {
                paramString += ", " + "const " + variable.getTypeDescriptor() + "& " + variable.getDescriptor();
            }
        }
        return paramString;
    }

    public void addInvoke(String method, Variable... params) {
        codeCpp.appendCppln(invoke(method, params));
    }

    public void addDefineStackVariable(Variable variable, Variable... params) {
        List<Variable> tmp = Arrays.asList(params);
        codeCpp.appendCppWordsln(variable.getTypeDescriptor(), variable.getDescriptor() + "{" + invokeParamString(tmp) + "}");
    }

    public void startFor(Variable item, String statement) {
        codeCpp.appendWordsln("for (" + item.getDescriptor() + " : " + statement + ") {");
        codeCpp.startBlock();
    }

    public void endFor() {
        codeCpp.endBlock();
        codeCpp.appendln("}");
    }

    public void addAssign(String left, String right) {
        codeCpp.appendCppWordsln(left, "=", right);
    }

    public String useVariable(Variable variable) {
        return variable.getDescriptor();
    }

    public String declareStackVariable(Variable variable) {
        codeCpp.addReference(variable.getType());
        return variable.getTypeDescriptor() + " " + variable.getDescriptor();
    }

    public String invoke(String method, Variable... params) {
        List<Variable> tmp = Arrays.asList(params);
        return method + "(" + invokeParamString(tmp) + ")";
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
}
