/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.python3.tools;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.langs.python3.Python3Code;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.UniqueList;

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
    private UniqueList<String, Variable> memberList = new UniqueList<>();
    private Set<String> importList = new HashSet<>();
    private List<Python3Code> methodList = new LinkedList<>();
    private VariableType superClass = null;
    private Python3Method defaultConstructor = null;

    public Python3Class(String className, VariableType superClass) {
        this(className);
        this.superClass = superClass;
        addReference(superClass);
    }

    public Python3Class(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void attachDefaultConstructor(Python3Method defaultConstructor) {
        this.defaultConstructor = defaultConstructor;
        writeMemberVariable();
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
            if (defaultConstructor == null) {
                writeMemberVariable();
            }
            code.appendCode(defaultConstructor.getCode());
            code.endln();
            writeMethods(code);
        });
    }

    private void writeMemberVariable() {
        if (defaultConstructor == null) {
            defaultConstructor = new Python3Method(this);
            defaultConstructor.getCode().appendWordsln("def __init__(self):");
            defaultConstructor.getCode().startBlock();
            if (superClass != null) {
                defaultConstructor.getCode().appendWordsln("super().__init__()");
            }
            if (memberList.values().size() == 0) {
                defaultConstructor.getCode().appendWordsln("pass");
            } else {
                memberList.values().forEach(item -> {
                    defaultConstructor.getCode().appendWordsln("self." + item.getDescriptor(), "=", "None");
                });
            }
            defaultConstructor.getCode().endBlock();
        }
        else {
            if (superClass != null) {
                defaultConstructor.getCode().appendWordsln("super().__init__()");
            }
            memberList.values().forEach(item -> {
                defaultConstructor.getCode().appendWordsln("self." + item.getDescriptor(), "=", "None");
            });
        }


    }

    @FunctionalInterface
    private interface ClassContent {
        void inClass();
    }

    private void defineClass(String clsName, ClassContent classContent) {
        String postClsString = ":";
        if (superClass != null) {
            postClsString = "(" + superClass.getDescriptor() + "):";
        }
        code.appendWordsln("class", clsName + postClsString);
        code.newBlock(classContent::inClass);
    }
}
