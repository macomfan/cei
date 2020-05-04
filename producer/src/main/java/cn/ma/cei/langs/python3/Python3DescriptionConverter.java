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
 * @author u0151316
 */
public class Python3DescriptionConverter implements IDescriptionConverter {

    private final Set<String> keywords;
    private final Set<String> buildIn;

    public Python3DescriptionConverter() {
        keywords = getKeywords();
        buildIn = getBuildIn();
    }

    private void checkInput(String name) {
        if (Checker.isEmpty(name)) {
            throw new CEIException("Name is null in NameConverter");
        }
    }

    private String checkKeyword(String name, String suffix) {
        if (keywords.contains(name)) {
            return name + suffix;
        }
        return name;
    }

    private String checkKeyWordAndBuildIn(String name, String suffix) {
        if (buildIn.contains(name) || keywords.contains(name)) {
            return name + suffix;
        }
        return name;
    }

    @Override
    public String getFileDescriptor(String name) {
        checkInput(name);
        return checkKeyword(WordSplitter.getLowercase(name), "");
    }

    @Override
    public String getModelDescriptor(String name) {
        checkInput(name);
        return checkKeyword(WordSplitter.getUpperCamelCase(name), "Model");
    }

    @Override
    public String getClientDescriptor(String name) {
        checkInput(name);
        return checkKeyword(WordSplitter.getUpperCamelCase(name), "Model");
    }

    @Override
    public String getVariableDescriptor(String name) {
        checkInput(name);
        return checkKeyWordAndBuildIn(WordSplitter.getLowercase(name, "_"), "_u");
    }

    @Override
    public String getMemberVariableDescriptor(String name) {
        checkInput(name);
        return checkKeyword(WordSplitter.getLowercase(name, "_"), "_u");
    }

    @Override
    public String getPrivateMemberDescriptor(String name) {
        checkInput(name);
        return "__" + checkKeyword(WordSplitter.getLowercase(name, "_"),"_u");
    }

    @Override
    public String getMethodDescriptor(String name) {
        checkInput(name);
        return checkKeyword(WordSplitter.getLowercase(name, "_"), "_func");
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

    public Set<String> getBuildIn() {
        Set<String> buildIn = new HashSet<>();
        buildIn.add("abs");
        buildIn.add("delattr");
        buildIn.add("hash");
        buildIn.add("memoryview");
        buildIn.add("set");
        buildIn.add("all");
        buildIn.add("dict");
        buildIn.add("help");
        buildIn.add("min");
        buildIn.add("setattr");
        buildIn.add("any");
        buildIn.add("dir");
        buildIn.add("hex");
        buildIn.add("next");
        buildIn.add("slice");
        buildIn.add("ascii");
        buildIn.add("divmod");
        buildIn.add("id");
        buildIn.add("object");
        buildIn.add("sorted");
        buildIn.add("bin");
        buildIn.add("enumerate");
        buildIn.add("input");
        buildIn.add("oct");
        buildIn.add("staticmethod");
        buildIn.add("bool");
        buildIn.add("eval");
        buildIn.add("int");
        buildIn.add("open");
        buildIn.add("str");
        buildIn.add("breakpoint");
        buildIn.add("exec");
        buildIn.add("isinstance");
        buildIn.add("ord");
        buildIn.add("sum");
        buildIn.add("bytearray");
        buildIn.add("filter");
        buildIn.add("issubclass");
        buildIn.add("pow");
        buildIn.add("super");
        buildIn.add("bytes");
        buildIn.add("float");
        buildIn.add("iter");
        buildIn.add("print");
        buildIn.add("tuple");
        buildIn.add("callable");
        buildIn.add("format");
        buildIn.add("len");
        buildIn.add("property");
        buildIn.add("type");
        buildIn.add("chr");
        buildIn.add("frozenset");
        buildIn.add("list");
        buildIn.add("range");
        buildIn.add("vars");
        buildIn.add("classmethod");
        buildIn.add("getattr");
        buildIn.add("locals");
        buildIn.add("repr");
        buildIn.add("zip");
        buildIn.add("compile");
        buildIn.add("globals");
        buildIn.add("map");
        buildIn.add("reversed");
        buildIn.add("__import__");
        buildIn.add("complex");
        buildIn.add("hasattr");
        buildIn.add("max");
        buildIn.add("round");
        return buildIn;
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
