package cn.ma.cei.generator.langs.java;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.Code;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.builder.MethodBuilder;
import cn.ma.cei.generator.environment.VariableList;

public class JavaCode extends Code {
    public JavaKeyword keyword = new JavaKeyword();

    public void appendPackage(String name) {
        if (name == null || name.equals("")) {
            throw new CEIException("Java package is null");
        }
        appendStatementWordsln("package", name);
    }

    public void appendImport(String name) {
        if (name != null && !name.equals("") && !name.equals(JavaKeyword.NO_REF)) {
            appendStatementWordsln("import", name);
        }
    }

    public void appendStatementln(String str) {
        appendln(str + ";");
    }


    private String defineParamString(VariableList params) {
        if (params == null) {
            return "";
        }
        String paramString = "";
        boolean isFirst = true;
        for (Variable variable : params.getVariableList()) {
            if (isFirst) {
                paramString += variable.type.getDescriptor() + " " + variable.name;
                isFirst = false;
            } else {
                paramString += ", " + variable.type.getDescriptor() + " " + variable.name;
            }
        }
        return paramString;
    }

//    public JavaCode invoke(String variable, String method, String... params) {
//        appendln(variable + "." + method + "(" + invokeParamString(params) + ");");
//        return this;
//    }

    public String toJavaString(String str) {
        return "\"" + str + "\"";
    }

    public JavaCode equalTo(String name, String value) {
        appendStatementWordsln(name, "=", value);
        return this;
    }

    public void appendStatementWordsln(String... args) {
        appendWords(args);
        appendln(";");
    }



    public void defineMethod(VariableType returnType, String methodName, VariableList params, MethodBuilder.MethodImplementation methodImplementation) {
        appendWordsln("public", returnType.getDescriptor(), methodName + "(" + defineParamString(params) + ") {");
        newBlock(() -> {
            methodImplementation.inMethod();
        });
        appendln("}");
        endln();
    }

    public void defineConstructor(String methodName, VariableList params, MethodBuilder.MethodImplementation methodImplementation) {
        appendWordsln("public", methodName, "(" + defineParamString(params) + ") {");
        newBlock(() -> {
            methodImplementation.inMethod();
        });
        appendln("}");
    }


}
