/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.python3.tools;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.langs.python3.Python3Code;
import cn.ma.cei.utils.UniquetList;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
    private UniquetList<String, Variable> memberList = new UniquetList<>();
    private Set<String> importList = new HashSet<>();
    private List<Python3Code> methodList = new LinkedList<>();

    public Python3Class(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void addMemberVariable(Variable memberVariable) {
        memberList.put(memberVariable.getName(), memberVariable);
        addReference(memberVariable.getType());
    }

    public void addReference(VariableType type) {
        importList.addAll(type.getReferences());
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
        memberList.values().forEach(item -> {
            code.appendWordsln(item.getDescriptor(), "=", "None");
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
