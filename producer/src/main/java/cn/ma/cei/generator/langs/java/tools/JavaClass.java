package cn.ma.cei.generator.langs.java.tools;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableList;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.environment.Reference;
import cn.ma.cei.generator.langs.java.JavaCode;
import cn.ma.cei.generator.langs.java.JavaKeyword;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class JavaClass {

    public enum AccessType {
        PUBLIC,
        PRIVATE
    }

    private String className = "";
    private String packageName = "";

    private VariableList privateMemberList = new VariableList();
    private VariableList publicMemberList = new VariableList();
    private Set<VariableType> importList = new HashSet<>();
    private List<JavaCode> methodList = new LinkedList<>();

    JavaCode code = new JavaCode();

    public JavaClass(String className, String packageName) {
        this.className = className;
        this.packageName = packageName.toLowerCase();
    }

    public void addMemberVariable(AccessType accessType, Variable memberVariable) {
        if (accessType == AccessType.PUBLIC) {
            publicMemberList.registerVariable(memberVariable);
        } else if (accessType == AccessType.PRIVATE) {
            privateMemberList.registerVariable(memberVariable);
        }
        addReference(memberVariable.type);
    }

    public void addMethod(JavaCode code) {
        methodList.add(code);
    }

    public void build(File folder) {
        writeReference(code);
        defineClass(className, () -> {
            code.endln();
            writeMemberVariable(code);
            writeMethods(code);
        });

        try {
            File newFile = new File(folder.getPath() + File.separator + className + ".java");
            newFile.createNewFile();
            FileWriter newFileWriter = new FileWriter(newFile);
            BufferedWriter bufferedWriter = new BufferedWriter(newFileWriter);
            bufferedWriter.write(code.toString());
            bufferedWriter.close();
            newFileWriter.close();
        } catch (Exception e) {

        }

    }

    public void addReference(VariableType type) {
        importList.add(type);
    }

    private void writeMethods(JavaCode code) {
        for (JavaCode method : methodList) {
            code.appendCode(method);
            code.endln();
        }
    }

    private void writeReference(JavaCode code) {
        code.appendPackage(packageName);
        code.endln();
        for (VariableType type : importList) {
            if (type.isGeneric()) {
                for (VariableType generic : type.getGenericList()) {
                    String typename = Reference.getReference(generic);
                    if (!typename.equals(JavaKeyword.NO_REF)) {
                        code.appendImport(typename);
                    }
                }
            } else {
                String typename = Reference.getReference(type);
                if (!typename.equals(JavaKeyword.NO_REF)) {
                    code.appendImport(typename);
                }
            }

        }
        code.endln();
    }

    private void writeMemberVariable(JavaCode code) {
        for (Variable variable : publicMemberList.getVariableList()) {
            code.appendStatementWordsln("public", variable.type.getDescriptor(), variable.nameDescriptor);
        }
        for (Variable variable : privateMemberList.getVariableList()) {
            code.appendStatementWordsln("private", variable.type.getDescriptor(), variable.nameDescriptor);
        }
        code.endln();
    }

    @FunctionalInterface
    private interface ClassContent {

        void inClass();
    }

    public void defineClass(String clsName, ClassContent classContent) {
        code.appendWordsln("public", "class", clsName, "{");
        code.newBlock(() -> {
            classContent.inClass();
        });
        code.appendln("}");
    }
}
