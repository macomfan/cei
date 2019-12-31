package cn.ma.cei.generator.langs.java.tools;

import cn.ma.cei.generator.builder.MethodBuilder;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableList;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.langs.java.JavaCode;
import cn.ma.cei.generator.langs.java.buildin.TheLinkedList;

public class JavaMethod {

    private JavaCode code = new JavaCode();
    private JavaClass parent;

    public JavaMethod(JavaClass parent) {
        this.parent = parent;
    }

    public JavaCode getCode() {
        return code;
    }

    public void newInstanceWithSpecialType(Variable variable, VariableType specialType, Variable... params) {
        String paramString = invokeParamString(params);
        code.appendStatementWordsln(variable.type.getDescriptor(), variable.nameDescriptor, "=", "new", specialType.getDescriptor() + "(" + paramString + ")");
    }

    public void newInstanceWithInvoke(Variable variable, String methodName, Variable... params) {
        String paramString = invokeParamString(params);
        code.appendStatementWordsln(variable.type.getDescriptor(), variable.nameDescriptor, "=", methodName + "(" + paramString + ")");
    }

    public void invoke(String methodName, Variable... params) {
        String paramString = invokeParamString(params);
        code.appendStatementWordsln(methodName + "(" + paramString + ")");
    }

    public void assignWithInvoke(Variable variable, String methodName, Variable... params) {
        String paramString = invokeParamString(params);
        code.appendStatementWordsln(variable.nameDescriptor, "=", methodName + "(" + paramString + ")");
    }

    public void assign(Variable variable, Variable value) {
        code.appendStatementWordsln(variable.nameDescriptor, "=", value.nameDescriptor);
    }

    public void newInstanceWithValue(Variable variable, Variable value) {
        code.appendStatementWordsln(variable.type.getDescriptor(), variable.nameDescriptor, "=", value.nameDescriptor);
    }

    public void newInstance(Variable variable) {
        code.appendStatementWordsln(variable.type.getDescriptor(), variable.nameDescriptor,
                "= new", variable.type.getDescriptor() + "()");
    }

    public void newInstance(Variable variable, Variable... params) {
        String paramString = invokeParamString(params);
        code.appendStatementWordsln(variable.type.getDescriptor(), variable.nameDescriptor,
                "= new", variable.type.getDescriptor() + "(" + paramString + ")");
    }

//    public void newInstance(VariableType type, String name, String... params) {
//        code.appendStatementWordsln(type.getDescriptor(), name, "= new", type.getDescriptor() + "(" + invokeParamString(params) + ")");
//    }
    public void newListVariable(Variable variable) {
        code.appendStatementWordsln(
                variable.nameDescriptor,
                "= new",
                TheLinkedList.getType().getDescriptor() + "<>()");
        parent.addReference(TheLinkedList.getType());
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

    private String invokeParamString(String... params) {
        if (params == null) {
            return "";
        }
        String paramString = "";
        boolean isFirst = true;
        for (String p : params) {
            if (isFirst) {
                paramString += p;
                isFirst = false;
            } else {
                paramString += ", " + p;
            }
        }
        return paramString;
    }

    public void startConstructor(String methodName, VariableList params) {
        code.appendWordsln("public", methodName, "(" + defineParamString(params) + ") {");
        code.startBlock();
    }

    public void startMethod(VariableType returnType, String methodName, VariableList params) {
        if (returnType == null) {
            code.appendWordsln("public", "void", methodName + "(" + defineParamString(params) + ") {");
        } else {
            code.appendWordsln("public", returnType.getDescriptor(), methodName + "(" + defineParamString(params) + ") {");
        }
        code.startBlock();
    }

    public void startStaticMethod(VariableType returnType, String methodName, VariableList params) {
        if (returnType == null) {
            code.appendWordsln("public static", "void", methodName + "(" + defineParamString(params) + ") {");
        } else {
            code.appendWordsln("public static", returnType.getDescriptor(), methodName + "(" + defineParamString(params) + ") {");
        }
        code.startBlock();
    }

    public void endMethod() {
        code.endBlock();
        code.appendln("}");
        code.endln();
    }

    private String defineParamString(VariableList params) {
        if (params == null) {
            return "";
        }
        String paramString = "";
        boolean isFirst = true;
        for (Variable variable : params.getVariableList()) {
            if (isFirst) {
                paramString += variable.type.getDescriptor() + " " + variable.nameDescriptor;
                isFirst = false;
            } else {
                paramString += ", " + variable.type.getDescriptor() + " " + variable.nameDescriptor;
            }
        }
        return paramString;
    }
}
