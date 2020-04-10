/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.python3;

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
public class Python3DescriptionConverter implements IDescriptionConverter {

    private Set<String> keywords;

    public Python3DescriptionConverter() {
        keywords = getKeywords();
    }

    private void checkInput(String name) {
        if (Checker.isEmpty(name)) {
            throw new CEIException("Name is null in NameConverter");
        }
    }

    private String checkKeyword(String name) {
        if (keywords.contains(name)) {
            throw new CEIException(name + " is keyword in Java");
        }
        return name;
    }

    @Override
    public String getFileDescriptor(String name) {
        checkInput(name);
        return checkKeyword(WordSplitter.getLowercase(name));
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
        return checkKeyword(WordSplitter.getLowercase(name, "_"));
    }

    @Override
    public String getMemberVariableDescriptor(String name) {
        checkInput(name);
        return checkKeyword(WordSplitter.getLowercase(name, "_"));
    }

    @Override
    public String getPrivateMemberDescriptor(String name) {
        checkInput(name);
        return "__" + checkKeyword(WordSplitter.getLowercase(name, "_"));
    }

    @Override
    public String getMethodDescriptor(String name) {
        checkInput(name);
        return checkKeyword(WordSplitter.getLowercase(name, "_"));
    }

    @Override
    public String toStringDescriptor(String name) {
        if (name != null) {
            String newName = name.replace("\\", "\\\\");
            return "\"" + newName + "\"";
        } else {
            throw new CEIException("Name is null in NameConverter");
        }
    }

    @Override
    public String getSelfDescriptor() {
        return "self";
    }

    @Override
    public String getGenericTypeDescriptor(String baseName, List<String> subNames) {
        return baseName;
    }

    @Override
    public Set<String> getKeywords() {
        Set<String> keywords = new HashSet<>();
        keywords.add("False");
        keywords.add("None");
        keywords.add("True");
        keywords.add("and");
        keywords.add("as");
        keywords.add("assert");
        keywords.add("break");
        keywords.add("class");
        keywords.add("continue");
        keywords.add("def");
        keywords.add("del");
        keywords.add("elif");
        keywords.add("else");
        keywords.add("except");
        keywords.add("finally");
        keywords.add("for");
        keywords.add("from");
        keywords.add("global");
        keywords.add("if");
        keywords.add("import");
        keywords.add("in");
        keywords.add("is");
        keywords.add("lambda");
        keywords.add("nonlocal");
        keywords.add("not");
        keywords.add("or");
        keywords.add("pass");
        keywords.add("raise");
        keywords.add("return");
        keywords.add("try");
        keywords.add("while");
        keywords.add("with");
        keywords.add("yield");
        return keywords;
    }
}
