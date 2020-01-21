package cn.ma.cei.generator.environment;

import cn.ma.cei.utils.NormalMap;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class VariableType {

    private final String typeName;
    private final List<VariableType> genericList = new LinkedList<>();

    public VariableType(String typeName, VariableType... subTypes) {
        if (subTypes != null && subTypes.length != 0) {
            Collections.addAll(genericList, subTypes);
        }
        this.typeName = typeName;
    }

    public boolean isGeneric() {
        return !genericList.isEmpty();
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

    public boolean equalTo(VariableType obj) {
        return this.equals(obj);
    }

    public boolean equalTo(String typeName) {
        return this.typeName.equals(typeName);
    }

    public boolean isCustomModel() {
        return VariableFactory.isCustomModel(this);
    }

    public boolean isCustomModelArray() {
        if (typeName.indexOf("array#") == -1) {
            return false;
        }
        if (genericList.isEmpty()) {
            return false;
        }
        return VariableFactory.isCustomModel(genericList.get(0));
    }

    // TODO remove it,
    // Calculate the descriptor in VariableFactory.
    public String getDescriptor() {
        return VariableFactory.getModelTypeDescriptor(this);
    }

    public List<String> getReferences() {
        return VariableFactory.getModelReferences(this);
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
