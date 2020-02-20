package cn.ma.cei.generator.langs.cpp.tools;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.langs.cpp.CodeForCpp;
import cn.ma.cei.generator.langs.cpp.CodeForHpp;
import java.util.List;

public class CppMethod {

    private CodeForHpp codeH = new CodeForHpp();
    private CodeForCpp codeCpp = new CodeForCpp();
    private String className;
    
    public CppMethod(String className) {
        this.className = className;
    }

    public CodeForCpp getCode() {
        return codeCpp;
    }

    public CodeForHpp getCodeForH() {
        return codeH;
    }

    public void defineMethod(VariableType returnType, String methodName, List<Variable> params) {
        codeH.appendStatementWordsln(returnType.getDescriptor(), methodName + "(" + defineParamString(params) + ")");

        codeCpp.appendWordsln(returnType.getDescriptor(), className + "::" + methodName + "(" + defineParamString(params) + ") {");
        codeCpp.newBlock(() -> {
            //methodImplementation.inMethod();
        });
        codeCpp.appendln("}");
        codeCpp.endln();
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
}
