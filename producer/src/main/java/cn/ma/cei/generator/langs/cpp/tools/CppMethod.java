package cn.ma.cei.generator.langs.cpp.tools;

import cn.ma.cei.generator.builder.MethodBuilder;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableList;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.langs.cpp.CodeForCpp;
import cn.ma.cei.generator.langs.cpp.CodeForHpp;

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

    public void defineMethod(VariableType returnType, String methodName, VariableList params, MethodBuilder.MethodImplementation methodImplementation) {
        codeH.appendStatementWordsln(returnType.getDescriptor(), methodName + "(" + defineParamString(params) + ")");

        codeCpp.appendWordsln(returnType.getDescriptor(), className + "::" + methodName + "(" + defineParamString(params) + ") {");
        codeCpp.newBlock(() -> {
            methodImplementation.inMethod();
        });
        codeCpp.appendln("}");
        codeCpp.endln();
    }

    private String defineParamString(VariableList params) {
        if (params == null) {
            return "";
        }
        String paramString = "";
        boolean isFirst = true;
        for (Variable variable : params.getVariableList()) {
            if (isFirst) {
                paramString += "const " + variable.type.getDescriptor() + "& " + variable.name;
                isFirst = false;
            } else {
                paramString += ", " + "const " + variable.type.getDescriptor() + "& " + variable.name;
            }
        }
        return paramString;
    }
}
