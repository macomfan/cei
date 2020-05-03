/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.java;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.naming.IDescriptionConverter;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.WordSplitter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author u0151316
 */
public class JavaDescriptionConverter implements IDescriptionConverter {

    private final Set<String> keywords;

    public JavaDescriptionConverter() {
        keywords = getKeywords();
    }

    private void checkInput(String name) {
        if (Checker.isEmpty(name)) {
            throw new CEIException("Name is null in NameConverter");
        }
    }

    private String checkKeyword(String name) {
        if (keywords.contains(name)) {
            return name + "_U";
        }
        return name;
    }

    @Override
    public String getFileDescriptor(String name) {
        checkInput(name);
        return WordSplitter.getUpperCamelCase(name);
    }

    @Override
    public String getModelDescriptor(String name) {
        checkInput(name);
        return checkKeyword(WordSplitter.getUpperCamelCase(name));
    }

    @Override
    public String getClientDescriptor(String name) {
        checkInput(name);
        return checkKeyword(WordSplitter.getUpperCamelCase(name));
    }

    @Override
    public String getVariableDescriptor(String name) {
        checkInput(name);
        return checkKeyword(WordSplitter.getLowerCamelCase(name));
    }

    @Override
    public String getMemberVariableDescriptor(String name) {
        checkInput(name);
        return checkKeyword(WordSplitter.getLowerCamelCase(name));
    }

    @Override
    public String getPrivateMemberDescriptor(String name) {
        checkInput(name);
        return checkKeyword(WordSplitter.getLowerCamelCase(name));
    }

    @Override
    public String getMethodDescriptor(String name) {
        checkInput(name);
        return checkKeyword(WordSplitter.getLowerCamelCase(name));
    }

    @Override
    public String toStringDescriptor(String name) {
        if (name != null) {
            String newName = name.replace("\\", "\\\\");
            return "\"" + newName + "\"";
        }
        else {
            throw new CEIException("Name is null in NameConverter");
        }
    }

    @Override
    public String getSelfDescriptor() {
        return "this";
    }

    @Override
    public String getGenericTypeDescriptor(String baseName, List<String> subNames) {
        StringBuilder stringBuilder = new StringBuilder();
        subNames.forEach((item) -> {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(item);
        });
        stringBuilder.insert(0, baseName + "<");
        stringBuilder.append(">");
        return stringBuilder.toString();
    }

    @Override
    public Set<String> getKeywords() {
        Set<String> keywords = new HashSet<>();
        keywords.add("abstract");
        keywords.add("assert");
        keywords.add("boolean");
        keywords.add("break");
        keywords.add("byte");
        keywords.add("case");
        keywords.add("catch");
        keywords.add("char");
        keywords.add("class");
        keywords.add("const");
        keywords.add("continue");
        keywords.add("default");
        keywords.add("do");
        keywords.add("double");
        keywords.add("else");
        keywords.add("enum");
        keywords.add("extends");
        keywords.add("final");
        keywords.add("finally");
        keywords.add("float");
        keywords.add("for");
        keywords.add("goto");
        keywords.add("if");
        keywords.add("implements");
        keywords.add("import");
        keywords.add("instanceof");
        keywords.add("int");
        keywords.add("interface");
        keywords.add("long");
        keywords.add("native");
        keywords.add("new");
        keywords.add("package");
        keywords.add("private");
        keywords.add("protected");
        keywords.add("public");
        keywords.add("return");
        keywords.add("strictfp");
        keywords.add("short");
        keywords.add("static");
        keywords.add("super");
        keywords.add("switch");
        keywords.add("synchronized");
        keywords.add("this");
        keywords.add("throw");
        keywords.add("throws");
        keywords.add("transient");
        keywords.add("try");
        keywords.add("void");
        keywords.add("volatile");
        keywords.add("while");
        return keywords;
    }

}
