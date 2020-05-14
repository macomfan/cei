package cn.ma.cei.langs.cpp;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.naming.IDescriptionConverter;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.WordSplitter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CppDescriptionConverter implements IDescriptionConverter {

    private final Set<String> keywords;

    public CppDescriptionConverter() {
        keywords = getKeywords();
    }

    private void checkInput(String name) {
        if (Checker.isEmpty(name)) {
            throw new CEIException("Name is null in NameConverter");
        }
    }

    private String checkKeyword(String name) {
        if (keywords.contains(name)) {
            return name + "U";
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
        } else {
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
    public String getReferenceByChain(String baseName, String valueName) {
        if (baseName.equals("this")) {
            return baseName + "->" + valueName;
        }
        else {
            return baseName + "." + valueName;
        }
    }

    @Override
    public Set<String> getKeywords() {
        Set<String> keywords = new HashSet<>();
        keywords.add("alignas");
        keywords.add("alignof");
        keywords.add("and");
        keywords.add("and_eq");
        keywords.add("asm");
        keywords.add("atomic_cancel");
        keywords.add("atomic_commit");
        keywords.add("atomic_noexcept");
        keywords.add("auto");
        keywords.add("bitand");
        keywords.add("bitor");
        keywords.add("bool");
        keywords.add("break");
        keywords.add("case");
        keywords.add("catch");
        keywords.add("char");
        keywords.add("char8_t");
        keywords.add("char16_t");
        keywords.add("char32_t");
        keywords.add("class");
        keywords.add("compl");
        keywords.add("concept");
        keywords.add("const");
        keywords.add("consteval");
        keywords.add("constexpr");
        keywords.add("constinit");
        keywords.add("const_cast");
        keywords.add("continue");
        keywords.add("co_await");
        keywords.add("co_return");
        keywords.add("co_yield");
        keywords.add("decltype");
        keywords.add("default");
        keywords.add("delete");
        keywords.add("do");
        keywords.add("double");
        keywords.add("dynamic_cast");
        keywords.add("else");
        keywords.add("enum");
        keywords.add("explicit");
        keywords.add("export");
        keywords.add("extern");
        keywords.add("false");
        keywords.add("float");
        keywords.add("for");
        keywords.add("friend");
        keywords.add("goto");
        keywords.add("if");
        keywords.add("inline");
        keywords.add("int");
        keywords.add("long");
        keywords.add("mutable");
        keywords.add("namespace");
        keywords.add("new");
        keywords.add("noexcept");
        keywords.add("not");
        keywords.add("not_eq");
        keywords.add("nullptr");
        keywords.add("operator");
        keywords.add("or");
        keywords.add("or_eq");
        keywords.add("private");
        keywords.add("protected");
        keywords.add("public");
        keywords.add("reflexpr");
        keywords.add("register");
        keywords.add("reinterpret_cast");
        keywords.add("requires");
        keywords.add("return");
        keywords.add("short");
        keywords.add("signed");
        keywords.add("sizeof");
        keywords.add("static");
        keywords.add("static_assert");
        keywords.add("static_cast");
        keywords.add("struct");
        keywords.add("switch");
        keywords.add("synchronized");
        keywords.add("template");
        keywords.add("this");
        keywords.add("thread_local");
        keywords.add("throw");
        keywords.add("true");
        keywords.add("try");
        keywords.add("typedef");
        keywords.add("typeid");
        keywords.add("typename");
        keywords.add("union");
        keywords.add("unsigned");
        keywords.add("using");
        keywords.add("virtual");
        keywords.add("void");
        keywords.add("volatile");
        keywords.add("wchar_t");
        keywords.add("while");
        keywords.add("xor");
        keywords.add("xor_eq");
        return keywords;
    }

    @Override
    public Set<String> getBuildIn() {
        return null;
    }
}
