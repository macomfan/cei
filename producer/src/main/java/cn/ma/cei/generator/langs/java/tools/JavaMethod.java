package cn.ma.cei.generator.langs.java.tools;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.langs.java.JavaCode;
import cn.ma.cei.generator.langs.java.buildin.TheLinkedList;

public class JavaMethod {

    private JavaCode code = new JavaCode();
    private JavaClass parent;
    
    public JavaMethod(JavaClass parent) {
        this.parent = parent;
    }

    public JavaCode getCode() {return code;}

    public void newInstance(VariableType type, String name, String... params) {
        code.appendStatementWordsln(type.getDescriptor(), name, "= new", type.getDescriptor() + "(" + invokeParamString(params) + ")");
    }
    
    public void newListVariable(Variable variable) {
        code.appendStatementWordsln(
                variable.nameDescriptor,
                "= new",
                TheLinkedList.getType().getDescriptor() + "<>()");
        parent.addReference(TheLinkedList.getType());
        
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
}
