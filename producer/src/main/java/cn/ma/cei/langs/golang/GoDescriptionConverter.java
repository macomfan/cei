/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.golang;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.buildin.WebSocketCallback;
import cn.ma.cei.generator.naming.IDescriptionConverter;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.WordSplitter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author U0151316
 */
public class GoDescriptionConverter implements IDescriptionConverter {

    private final Set<String> keywords;

    public GoDescriptionConverter() {
        keywords = getKeywords();
    }

    private void checkInput(String name) {
        if (Checker.isEmpty(name)) {
            throw new CEIException("Name is null in NameConverter");
        }
    }

    private String checkKeyword(String name) {
        if (keywords.contains(name)) {
            throw new CEIException(name + " is keyword in Go");
        }
        return name;
    }

    @Override
    public String getFileDescriptor(String name) {
        checkInput(name);
        return WordSplitter.getLowercase(name);
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
        return checkKeyword(WordSplitter.getUpperCamelCase(name));
    }

    @Override
    public String getPrivateMemberDescriptor(String name) {
        checkInput(name);
        return checkKeyword(WordSplitter.getLowerCamelCase(name));
    }

    @Override
    public String getMethodDescriptor(String name) {
        checkInput(name);
        return checkKeyword(WordSplitter.getUpperCamelCase(name));
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
        return "inst";
    }

    @Override
    public String getGenericTypeDescriptor(String baseName, List<String> subNames) {
//        if (subNames.size() > 1) {
//            throw new CEIException("Type error in Go");
//        }
//        if (subNames.size() == 1) {
//            return baseName + subNames.get(0);
//        }
        if ("[]".equals(baseName)) {
            return baseName + subNames.get(0);
        }
        else if ("impl.WebSocketCallback".equals(baseName)) {
            return "func (connection *impl.WebSocketConnection)";
        }
        return baseName;
    }

    @Override
    public Set<String> getKeywords() {
        Set<String> keywords = new HashSet<>();
        keywords.add("break");
        keywords.add("default");
        keywords.add("func");
        keywords.add("interface");
        keywords.add("select");
        keywords.add("case");
        keywords.add("defer");
        keywords.add("go");
        keywords.add("map");
        keywords.add("struct");
        keywords.add("chan");
        keywords.add("else");
        keywords.add("goto");
        keywords.add("package");
        keywords.add("switch");
        keywords.add("const");
        keywords.add("fallthrough");
        keywords.add("if");
        keywords.add("range");
        keywords.add("type");
        keywords.add("continue");
        keywords.add("for");
        keywords.add("import");
        keywords.add("return");
        keywords.add("var");
        keywords.add("append");
        keywords.add("bool");
        keywords.add("byte");
        keywords.add("cap");
        keywords.add("close");
        keywords.add("complex");
        keywords.add("complex64");
        keywords.add("complex128");
        keywords.add("uint16");
        keywords.add("copy");
        keywords.add("false");
        keywords.add("float32");
        keywords.add("float64");
        keywords.add("imag");
        keywords.add("int");
        keywords.add("int8");
        keywords.add("int16");
        keywords.add("uint32");
        keywords.add("int32");
        keywords.add("int64");
        keywords.add("iota");
        keywords.add("len");
        keywords.add("make");
        keywords.add("new");
        keywords.add("nil");
        keywords.add("panic");
        keywords.add("uint64");
        keywords.add("print");
        keywords.add("println");
        keywords.add("real");
        keywords.add("recover");
        keywords.add("string");
        keywords.add("true");
        keywords.add("uint");
        keywords.add("uint8");
        keywords.add("uintptr");
        return keywords;
    }

    @Override
    public Set<String> getBuildIn() {
        return null;
    }
}
