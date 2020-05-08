package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.exception.CEIException;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.TwoTuple;
import cn.ma.cei.utils.UniqueList;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class VariableType {

    public static VariableType VOID = new VariableType("##CEI_VOID_TYPE", null);

    private final String typeName;

    private final UniqueList<String, sMethod> methods = new UniqueList<>();
    private final UniqueList<String, Variable> members = new UniqueList<>();
    private final List<VariableType> genericList = new LinkedList<>();
    private String baseName;

    private VariableType(String typeName, String baseName, VariableType... subTypes) {
        if (subTypes != null && subTypes.length != 0) {
            Collections.addAll(genericList, subTypes);
        }
        this.baseName = baseName;
        this.typeName = typeName;
    }

    public sMethod createMethod(String methodName) {
        if (methods.containsKey(methodName)) {
            CEIErrors.showXMLFailure("[VariableType] Duplicate method: " + methodName);
        }
        sMethod method = new sMethod(this, methodName);
        methods.put(methodName, method);
        return method;
    }

    public sMethod getMethod(String methodName) {
        if (methods.containsKey(methodName)) {
            return methods.get(methodName);
        }
        return null;
    }

    public Variable addPrivateMember(VariableType type, String memberName) {
        if (members.containsKey(memberName)) {
            CEIErrors.showXMLFailure("Duplicate member: %s in model: %s", memberName, typeName);
        }
        Variable member = VariableCreator.createVariable(type, memberName, Variable.Position.PRIVATE);
        members.put(memberName, member);
        return member;
    }

    public Variable addMember(VariableType type, String memberName) {
        if (members.containsKey(memberName)) {
            CEIErrors.showXMLFailure("Duplicate member: %s in model: %s", memberName, typeName);
        }
        Variable member = VariableCreator.createVariable(type, memberName, Variable.Position.MEMBER);
        members.put(memberName, member);
        return member;
    }

    public Variable getMember(String memberName) {
        return members.get(memberName);
    }

    public Variable tryGetMember(String memberName) {
        Variable result = members.get(memberName);
        if (result == null) {
            CEIErrors.showXMLFailure("Cannot get the member: %s in model: %s", memberName, typeName);
        }
        return result;
    }

    public boolean isBased(VariableType type) {
        if (!Checker.isEmpty(baseName) && baseName.equals(type.typeName)) {
            return true;
        } else return false;
    }

    public List<VariableType> getGenericList() {
        return new LinkedList<>(genericList);
    }

    public String getName() {
        return typeName;
    }

    public boolean isValid() {
        return !(typeName == null || typeName.equals(""));
    }

    public boolean equalTo(String typeName) {
        return this.typeName.equals(typeName);
    }

    public String getDescriptor() {
        TwoTuple<String, List<String>> modelInfo = GlobalContext.getModelInfo(typeName);
        if (modelInfo == null) {
            throw new CEIException("Cannot find the model " + typeName);
        }
        return GlobalContext.getModelInfo(typeName).get1();
    }

    public List<String> getReferences() {
        TwoTuple<String, List<String>> modelInfo = GlobalContext.getModelInfo(typeName);
        if (modelInfo == null) {
            CEIErrors.showXMLFailure("Cannot find the model " + typeName);
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
