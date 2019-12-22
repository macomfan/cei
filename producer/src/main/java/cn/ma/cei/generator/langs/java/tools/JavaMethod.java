package cn.ma.cei.generator.langs.java.tools;

import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.langs.java.JavaCode;

public class JavaMethod {

    private JavaCode code = new JavaCode();

    public JavaCode getCode() {return code;}

    public void newInstance(VariableType type, String name, String... params) {
        code.appendStatementWordsln(type.getDescriptor(), name, "= new", type.getDescriptor() + "(" + invokeParamString(params) + ")");
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
