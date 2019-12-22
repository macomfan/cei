package cn.ma.cei.generator.langs.cpp.tools;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableList;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.langs.cpp.CodeForCpp;
import cn.ma.cei.generator.langs.cpp.CodeForHpp;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CppClass {
    private CodeForCpp codeCpp = new CodeForCpp();
    private CodeForHpp codeH = new CodeForHpp();

    private VariableList privateMemberList = new VariableList();
    private VariableList publicMemberList = new VariableList();
    private Set<VariableType> importList = new HashSet<>();
    private List<CppMethod> methodList = new LinkedList<>();

    public enum AccessType {
        PUBLIC,
        PRIVATE
    }


    private String className;

    public CppClass(String className) {
        this.className = className;
    }

    public void addMemberVariable(AccessType accessType, Variable memberVariable) {
        if (accessType == AccessType.PUBLIC) {
            publicMemberList.registerVariable(memberVariable);
        } else if (accessType == AccessType.PRIVATE) {
            privateMemberList.registerVariable(memberVariable);
        }
        addReference(memberVariable.type);
    }

    public void addReference(VariableType type) {
        importList.add(type);
    }

    private void writeMemberVariable() {
//        for (Variable variable : publicMemberList.getVariableList()) {
//            code.appendStatementWordsln("public", Database.getTypeNameReference().getTypeName(variable.type), variable.name);
//        }
//        for (Variable variable : privateMemberList.getVariableList()) {
//            code.appendStatementWordsln("private", Database.getTypeNameReference().getTypeName(variable.type), variable.name);
//        }
//        code.endln();
    }
}
