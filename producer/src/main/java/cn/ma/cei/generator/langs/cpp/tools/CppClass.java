package cn.ma.cei.generator.langs.cpp.tools;

import cn.ma.cei.generator.environment.Reference;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableList;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.langs.cpp.CodeForCpp;
import cn.ma.cei.generator.langs.cpp.CodeForHpp;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

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
    private String exchangeName;

    public CppClass(String exchangName, String className) {
        this.className = className;
        this.exchangeName = exchangName;
    }

    public String getClassName() {
        return className;
    }

    private void writeNamespace() {
        codeH.appendWordsln("namespace", exchangeName, "{");
        codeCpp.appendWordsln("namespace", exchangeName, "{");
        codeCpp.startBlock();
        codeH.startBlock();
    }

    private void writeReference() {
        codeH.appendln("#pragma once");
        codeH.endln();

        importList.forEach((type) -> {
            if (type.isGeneric()) {
                for (VariableType generic : type.getGenericList()) {
                    String typename = Reference.getReference(generic);
                    if (!typename.equals("")) {
                        codeH.appendInclude(typename);
                    }
                }
            } else {
                String typename = Reference.getReference(type);
                codeH.appendInclude(typename);
            }
        });
        codeH.endln();
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

    public void addMethod(CppMethod method) {
        methodList.add(method);
    }

    private void writeMemberVariable() {
        if (!publicMemberList.isEmpty()) {
            codeH.appendln("public:");
            codeH.newBlock(() -> {
                publicMemberList.getVariableList().forEach((variable) -> {
                    codeH.appendStatementWordsln(variable.type.getDescriptor(), variable.nameDescriptor);
                });
            });
        }
        if (!privateMemberList.isEmpty()) {
            privateMemberList.getVariableList().forEach((variable) -> {
                codeH.appendStatementWordsln(variable.type.getDescriptor(), variable.nameDescriptor);
            });
        }
    }

    public void build() {
        writeReference();
        writeNamespace();
        defineClass(() -> {
            writeMemberVariable();

            codeH.appendln("public:");
            codeH.newBlock(() -> {
                methodList.forEach((method) -> {
                    codeH.appendCode(method.getCodeForH());
                    codeCpp.appendCode(method.getCode());
                });
            });
        });
        codeCpp.endBlock();
        codeH.endBlock();
        codeCpp.appendln("}");
        codeH.appendln("}");

        try {
            {
                File newFile = new File("C:\\dev\\cei\\framework\\cei_cpp\\" + className + ".h");
                newFile.createNewFile();
                FileWriter newFileWriter = new FileWriter(newFile);
                BufferedWriter bufferedWriter = new BufferedWriter(newFileWriter);
                bufferedWriter.write(codeH.toString());
                bufferedWriter.close();
                newFileWriter.close();
            }
            {
                if (!methodList.isEmpty()) {
                    File newFile = new File("C:\\dev\\cei\\framework\\cei_cpp\\" + className + ".cpp");
                    newFile.createNewFile();
                    FileWriter newFileWriter = new FileWriter(newFile);
                    BufferedWriter bufferedWriter = new BufferedWriter(newFileWriter);
                    bufferedWriter.write(codeCpp.toString());
                    bufferedWriter.close();
                    newFileWriter.close();
                }
            }
        } catch (Exception e) {

        }
    }

    @FunctionalInterface
    private interface ClassContent {

        void inClass();
    }

    public void defineClass(ClassContent classContent) {
        codeH.appendWordsln("class", className, "{");
        classContent.inClass();
        codeH.appendln("};");

    }
}
