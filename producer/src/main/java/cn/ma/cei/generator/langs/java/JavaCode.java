package cn.ma.cei.generator.langs.java;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.Code;

public class JavaCode extends Code {

    public static final String NO_REF = "NO_REF";
    public static final String CURRENT_PACKAGE = "cn.ma.cei.exchanges";

    public void appendPackage(String name) {
        if (name == null || name.equals("")) {
            throw new CEIException("Java package is null");
        }
        appendStatementWordsln("package", name);
    }

    public void appendImport(String name) {
        if (name != null && !name.equals("") && !name.equals(JavaCode.NO_REF)) {
            appendStatementWordsln("import", name);
        }
    }

    public void appendStatementln(String str) {
        appendln(str + ";");
    }

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
}
