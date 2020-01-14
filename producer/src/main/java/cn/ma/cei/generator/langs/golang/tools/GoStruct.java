/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.golang.tools;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.langs.golang.GoCode;
import cn.ma.cei.utils.UniquetList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author U0151316
 */
public class GoStruct {

    private GoCode code = new GoCode();
    private String structName;

    private UniquetList<String, GoVar> memberList = new UniquetList<>();
    private Set<String> importList = new HashSet<>();
    private List<GoMethod> methodList = new LinkedList<>();

    public GoStruct(String structName) {
        this.structName = structName;
    }

    public GoCode getCode() {
        return code;
    }

    public void addMember(GoVar memberVariable) {
        memberList.put(memberVariable.getName(), memberVariable);
    }


    public void addMethod(GoMethod method) {
        methodList.add(method);
    }

    public String getStructName() {
        return structName;
    }

    public void addReference(VariableType type) {
        importList.addAll(type.getReferences());
    }

    public Set<String> getImportList() {
        return importList;
    }

    public void build() {
        defineStruct();
    }

    private void defineStruct() {
        code.appendWordsln("type", structName, "struct", "{");
        code.newBlock(() -> {
            defineMembers();
        });
        code.appendln("}");
        writeMethod();
    }

    private void defineMembers() {
        int maxMemberLen = 0;

        for (GoVar member : memberList.values()) {
            if (member.getNameDescriptor().length() > maxMemberLen) {
                maxMemberLen = member.getNameDescriptor().length();
            }
        }
        maxMemberLen++;
        for (GoVar member : memberList.values()) {
            code.addMemberln(member.getNameDescriptor(), member.getTypeDescriptor(), maxMemberLen);
        }
    }

    private void writeMethod() {
        if (!methodList.isEmpty()) {
            code.endln();
        }
        methodList.forEach(method -> {
            code.appendCode(method.getCode());
            code.endln();
        });
    }
}
