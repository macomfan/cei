/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.java;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.Code;

/**
 *
 * @author u0151316
 */
public class JavaCode extends Code {

    public static final String CURRENT_PACKAGE = "cn.ma.cei.exchanges";
    public final static String NO_REF = "NO_REF";

    public void appendPackage(String name) {
        if (name == null || name.equals("")) {
            throw new CEIException("Java package is null");
        }
        appendJavaLine("package", name);
    }

    public void appendImport(String name) {
        if (name != null && !name.equals("") && !name.equals(JavaCode.NO_REF)) {
            appendJavaLine("import", name);
        }
    }
    
    public void appendJavaLine(String... statements) {
        appendWords(statements);
        appendln(";");
    }
}
