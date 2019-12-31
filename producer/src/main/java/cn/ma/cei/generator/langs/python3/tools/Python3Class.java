/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.python3.tools;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.CEIPath;
import cn.ma.cei.generator.environment.Environment;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableList;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.langs.python3.Python3Code;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author U0151316
 */
public class Python3Class {

    enum ClassType {
        STANDARD,
        INNER
    }

    private final String className;
    private final String fileName;
    private Python3Code code = new Python3Code();
    private VariableList memberList = new VariableList();
    private Set<String> importList = new HashSet<>();
    private Map<String, Python3Class> innerClasses = new HashMap<>();
    private List<Python3Code> methodList = new LinkedList<>();
    private ClassType type = ClassType.STANDARD;

    public Python3Class(String className) {
        this.className = className;
        this.fileName = Environment.getCurrentDescriptionConverter().getFileDescriptor(className);
    }

    public String getFilename() {
        return fileName;
    }

    public void addMemberVariable(Variable memberVariable) {
        memberList.registerVariable(memberVariable);
        addReference(memberVariable.type);
    }

    public void addReference(VariableType type) {
        if (type.isGeneric()) {
            importList.addAll(type.getReferences());
        } else {
            importList.add(type.getReference());
        }
    }

    public void addMethod(Python3Method method) {
        methodList.add(method.getCode());
    }

    public void addInnerClass(Python3Class innerClass) {
        if (innerClass.type != ClassType.INNER) {
            innerClass.type = ClassType.INNER;
        }
        if (innerClasses.containsKey(innerClass.className)) {
            throw new CEIException("[JavaClass] Cannot add duplite class");
        }
        innerClasses.put(innerClass.className, innerClass);
    }

    private void writeMethods(Python3Code code) {
        for (Python3Code method : methodList) {
            code.appendCode(method);
            code.endln();
        }
    }

    public void build(CEIPath folder) {
        if (type == ClassType.INNER) {
            defineClass(className, () -> {
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

            CEIPath file = CEIPath.appendFile(
                    folder,
                    fileName + ".py");
            file.write(code.toString());
        }
    }

    private void writeMemberVariable(Python3Code code) {
        memberList.getVariableList().forEach(item -> {
            code.appendWordsln(item.nameDescriptor, "=", "None");
        });
    }

    @FunctionalInterface
    private interface ClassContent {

        void inClass();
    }

    private void defineClass(String clsName, ClassContent classContent) {
        code.appendWordsln("class", clsName, ":");
        code.newBlock(() -> {
            classContent.inClass();
        });
    }

    private void writeReference(Python3Code code) {
        Set<String> newImportList = new HashSet<>();

        innerClasses.values().forEach(value -> {
            newImportList.addAll(value.importList);
        });
        newImportList.addAll(importList);

        List<String> list = new ArrayList<>(newImportList);
        Collections.sort(list);
        list.forEach((item) -> {
            if (!item.equals(Python3Code.NO_REF)) {
                code.appendln(item);
            }
        });
        code.endln();
        code.endln();
    }
}
