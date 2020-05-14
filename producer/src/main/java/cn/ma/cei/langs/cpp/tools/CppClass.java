package cn.ma.cei.langs.cpp.tools;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.UniqueList;

import java.util.LinkedList;
import java.util.List;

public class CppClass {

    private final CodeForCpp codeCpp = new CodeForCpp();
    private final CodeForHpp codeH = new CodeForHpp();

    private final UniqueList<String, Variable> privateMemberList = new UniqueList();
    final UniqueList<String, Variable> publicMemberList = new UniqueList();

    private final List<CppMethod> methodList = new LinkedList<>();
    private final String className;

    public CppClass(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public CodeForCpp getCode() {
        return codeCpp;
    }

    public CodeForHpp getCodeH() {
        return codeH;
    }

    public void addMemberVariable(AccessType accessType, Variable memberVariable) {
        if (accessType == AccessType.PUBLIC) {
            publicMemberList.put(memberVariable.getName(), memberVariable);
        } else if (accessType == AccessType.PRIVATE) {
            privateMemberList.put(memberVariable.getName(), memberVariable);
        }
        addReferenceH(memberVariable.getType());
    }

    public void addReferenceH(VariableType type) {
        codeH.addReference(type);
    }

    public void addMethod(CppMethod method) {
        methodList.add(method);
    }

    private void writeMemberVariable() {
        if (!publicMemberList.isEmpty()) {
            codeH.appendln("public:");
            codeH.newBlock(() -> {
                publicMemberList.values().forEach((variable) -> {
                    codeH.appendCppWordsln(variable.getTypeDescriptor(), variable.getDescriptor());
                });
            });
        }
        if (!privateMemberList.isEmpty()) {
            privateMemberList.values().forEach((variable) -> {
                codeH.appendCppWordsln(variable.getTypeDescriptor(), variable.getDescriptor());
            });
        }
    }

    public void build() {
        codeH.appendWordsln("class", className, "{");
        writeMemberVariable();
        if (!Checker.isNull(methodList)) {
            codeH.appendln("public:");
            codeH.newBlock(() -> methodList.forEach((method) -> {
                codeH.appendCode(method.getCodeForH());
                codeCpp.appendCode(method.getCode());
            }));
        }
        codeH.appendln("};");
    }

    public enum AccessType {
        PUBLIC,
        PRIVATE
    }
}
