package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.utils.TwoTuple;
import cn.ma.cei.utils.UniquetList;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class VariableType {

    private UniquetList<String, sMethod> methods = new UniquetList<>();
    private UniquetList<String, Variable> members = new UniquetList<>();

    private final String typeName;
    private final List<VariableType> genericList = new LinkedList<>();

    public VariableType(String typeName, VariableType... subTypes) {
        if (subTypes != null && subTypes.length != 0) {
            Collections.addAll(genericList, subTypes);
        }
        this.typeName = typeName;
    }

    public sMethod createMethod(String methodName) {
        if (methods.containsKey(methodName)) {
            throw new CEIException("[VariableType] Duplicate method: " + methodName);
        }
        sMethod method = new sMethod(methodName);
        methods.put(methodName, method);
        return method;
    }

    public Variable addMember(VariableType type, String memberName) {
        if (members.containsKey(memberName)) {
            throw new CEIException("[VariableType] Duplicate member: " + memberName);
        }
        Variable member = VariableCreator.createMemberVariable(type, memberName);
        members.put(memberName, member);
        return member;
    }

    public Variable getMember(String memberName) {
        return members.get(memberName);
    }

//    public boolean isGeneric() {
//        return !genericList.isEmpty();
//    }

    public List<VariableType> getGenericList() {
        return new LinkedList<>(genericList);
    }

    public String getName() {
        return typeName;
    }

    public boolean isValid() {
        return !(typeName == null || typeName.equals(""));
    }

//    public boolean equalTo(VariableType obj) {
//        return this.equals(obj);
//    }

    public boolean equalTo(String typeName) {
        return this.typeName.equals(typeName);
    }

//    public boolean isCustomModel() {
//        return VariableFactory.isCustomModel(this);
//    }

//    public boolean isCustomModelArray() {
//        if (!typeName.contains("array#")) {
//            return false;
//        }
//        if (genericList.isEmpty()) {
//            return false;
//        }
//        return VariableFactory.isCustomModel(genericList.get(0));
//    }

    // TODO remove it,
    // Calculate the descriptor in VariableFactory.
    public String getDescriptor() {
        TwoTuple<String, List<String>> modelInfo = GlobalContext.getModelInfo(typeName);
        if (modelInfo == null) {
            throw new CEIException("[] cannot find the model " + typeName);
        }
        return GlobalContext.getModelInfo(typeName).get1();
    }

    public List<String> getReferences() {
        TwoTuple<String, List<String>> modelInfo = GlobalContext.getModelInfo(typeName);
        if (modelInfo == null) {
            throw new CEIException("[] cannot find the model " + typeName);
        }
        return GlobalContext.getModelInfo(typeName).get2();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VariableType tmp = (VariableType) o;
        return typeName.equals(tmp.typeName);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.typeName);
        return hash;
    }
}
