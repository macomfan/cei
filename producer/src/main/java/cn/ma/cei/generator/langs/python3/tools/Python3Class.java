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
    private Python3Code code = new Python3Code();
    private VariableList memberList = new VariableList();
    private Set<String> importList = new HashSet<>();
    private List<Python3Code> methodList = new LinkedList<>();

    public Python3Class(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
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

    public Python3Code getCode() {
        return code;
    }

    public Set<String> getImportList() {
        return importList;
    }

    private void writeMethods(Python3Code code) {
        for (Python3Code method : methodList) {
            code.appendCode(method);
            code.endln();
        }
    }

    public void build() {
        defineClass(className, () -> {
            writeMemberVariable(code);
            writeMethods(code);
        });
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
        code.appendWordsln("class", clsName + ":");
        code.newBlock(() -> {
            classContent.inClass();
        });
    }
}
