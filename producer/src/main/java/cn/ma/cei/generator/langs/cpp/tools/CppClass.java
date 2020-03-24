 package cn.ma.cei.generator.langs.cpp.tools;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.langs.cpp.CodeForCpp;
import cn.ma.cei.generator.langs.cpp.CodeForHpp;
import cn.ma.cei.utils.UniqueList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CppClass {

    private final CodeForCpp codeCpp = new CodeForCpp();
    private final CodeForHpp codeH = new CodeForHpp();

    private final UniqueList<String, Variable> privateMemberList = new UniqueList();
    private final UniqueList<String, Variable> publicMemberList = new UniqueList();
    private final Set<VariableType> importList = new HashSet<>();
    private final List<CppMethod> methodList = new LinkedList<>();

    public enum AccessType {
        PUBLIC,
        PRIVATE
    }

    private final String className;
    private final String exchangeName;

    public CppClass(String exchangeName, String className) {
        this.className = className;
        this.exchangeName = exchangeName;
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
//            if (type.isGeneric()) {
//                for (VariableType generic : type.getGenericList()) {
//                    String typename = VariableFactory.getModelReference(generic);
//                    if (!typename.equals("")) {
//                        codeH.appendInclude(typename);
//                    }
//                }
//            } else {
//                String typename = VariableFactory.getModelReference(type);
//                codeH.appendInclude(typename);
//            }
        });
        codeH.endln();
    }

    public void addMemberVariable(AccessType accessType, Variable memberVariable) {
        if (accessType == AccessType.PUBLIC) {
            publicMemberList.put(memberVariable.getName(), memberVariable);
        } else if (accessType == AccessType.PRIVATE) {
            privateMemberList.put(memberVariable.getName(), memberVariable);
        }
        addReference(memberVariable.getType());
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
                publicMemberList.values().forEach((variable) -> {
                    codeH.appendStatementWordsln(variable.getTypeDescriptor(), variable.getDescriptor());
                });
            });
        }
        if (!privateMemberList.isEmpty()) {
            privateMemberList.values().forEach((variable) -> {
                codeH.appendStatementWordsln(variable.getTypeDescriptor(), variable.getDescriptor());
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
