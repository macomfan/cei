/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.cpp;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.Code;
import cn.ma.cei.generator.VariableType;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author U0151316
 */
public class CppCode extends Code {

    private final Set<String> includeList = new HashSet<>();

    public void addReference(VariableType type) {
        includeList.addAll(type.getReferences());
    }

    public Set<String> getIncludeList() {
        return includeList;
    }

    public void appendCppWordsln(String... args) {
        appendWords(args);
        appendln(";");
    }

    public void appendCppln(String str) {
        appendln(str + ";");
    }

    public void appendInclude(String name) {
        if (name != null && !name.equals("") && !name.equals(BuilderContext.NO_REF)) {
            appendWordsln("#include", name);
        }
    }
    
    public String toCppString(String str) {
        return "\"" + str + "\"";
    }
}
