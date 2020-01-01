package cn.ma.cei.generator.langs.java.tools;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.CEIPath;
import cn.ma.cei.generator.environment.Reference;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableList;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.langs.java.JavaCode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JavaClass {

    enum ClassType {
        STANDARD,
        INNER
    }

    public enum AccessType {
        PUBLIC,
        PRIVATE
    }

    private String className = "";
    private String packageName = "";
    private ClassType type = ClassType.STANDARD;

    private VariableList privateMemberList = new VariableList();
    private VariableList publicMemberList = new VariableList();
    private Set<String> importList = new HashSet<>();
    private List<JavaMethod> methodList = new LinkedList<>();

    private Map<String, JavaClass> innerClasses = new HashMap<>();

    JavaCode code = new JavaCode();

    public JavaClass(String className, String packageName) {
        this.className = className;
        this.packageName = packageName.toLowerCase();
        this.type = ClassType.STANDARD;
    }

    public JavaClass(String className) {
        this.className = className;
        this.packageName = null;
        this.type = ClassType.INNER;
    }

    public String getClassName() {
        return className;
    }

    public void addInnerClass(JavaClass innerClass) {
        if (innerClass.type != ClassType.INNER) {
            innerClass.type = ClassType.INNER;
            innerClass.packageName = null;
        }
        if (innerClasses.containsKey(innerClass.className)) {
            throw new CEIException("[JavaClass] Cannot add duplite class");
        }
        innerClasses.put(innerClass.className, innerClass);
    }

    public void addMemberVariable(AccessType accessType, Variable memberVariable) {
        if (accessType == AccessType.PUBLIC) {
            publicMemberList.registerVariable(memberVariable);
        } else if (accessType == AccessType.PRIVATE) {
            privateMemberList.registerVariable(memberVariable);
        }
        addReference(memberVariable.type);
    }

    public void addMethod(JavaMethod method) {
        methodList.add(method);
    }

    public void build(CEIPath folder) {
        if (type == ClassType.INNER) {
            defineClass(className, () -> {
                code.endln();
                writeMemberVariable(code);
                writeMethods(code);
            });
        } else {
            writeReference(code);

            defineClass(className, () -> {
                code.endln();
                innerClasses.values().forEach(value -> {
                    value.build(folder);
                    code.appendCode(value.code);
                    code.endln();
                });
                writeMemberVariable(code);
                writeMethods(code);
            });

            CEIPath file = CEIPath.appendFile(folder, className + ".java");
            file.write(code.toString());
        }
    }

    public void addReference(VariableType type) {
        if (type.isGeneric()) {
            importList.addAll(type.getReferences());
        } else {
            importList.add(type.getReference());
        }
    }

    private void writeMethods(JavaCode code) {
        methodList.forEach(method -> {
            code.appendCode(method.getCode());
            code.endln();
        });
    }

    private void writeReference(JavaCode code) {
        code.appendPackage(packageName);
        code.endln();

        Set<String> newImportList = new HashSet<>();

        innerClasses.values().forEach(value -> {
            newImportList.addAll(value.importList);
        });
        newImportList.addAll(importList);

        List<String> list = new ArrayList<>(newImportList);
        Collections.sort(list);
        list.forEach((item) -> {
            if (!item.equals(Reference.NO_REF) && !item.equals(packageName)) {
                code.appendImport(item);
            }
        });
        code.endln();
    }

    private void writeMemberVariable(JavaCode code) {
        publicMemberList.getVariableList().forEach(variable -> {
            code.appendJavaLine("public", variable.type.getDescriptor(), variable.nameDescriptor);
        });
        privateMemberList.getVariableList().forEach(variable -> {
            code.appendJavaLine("private", variable.type.getDescriptor(), variable.nameDescriptor);
        });
        code.endln();
    }

    @FunctionalInterface
    private interface ClassContent {

        void inClass();
    }

    private void defineClass(String clsName, ClassContent classContent) {
        String header;
        if (type == ClassType.INNER) {
            header = "static public class";
        } else {
            header = "public class";
        }
        code.appendWordsln(header, clsName, "{");
        code.newBlock(() -> {
            classContent.inClass();
        });
        code.appendln("}");
    }
}
