package cn.ma.cei.generator.environment;

import cn.ma.cei.generator.environment.Reference;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class VariableType {

    private final String type;
    private final List<VariableType> genericList = new LinkedList<>();
    private String typeDescriptor = null;
    private String typeReference = null;

    private VariableType(String type) {
        this.type = type;
    }

    public boolean isGeneric() {
        return !genericList.isEmpty();
    }

    private void addGeneric(VariableType variableType) {
        genericList.add(variableType);
    }

    public List<VariableType> getGenericList() {
        List<VariableType> res = new LinkedList<>();
        res.addAll(genericList);
        return res;
    }

    public String getName() {
        return type;
    }

    public boolean isValid() {
        return !(type == null || type.equals(""));
    }

    public String getDescriptor() {
        if (typeDescriptor == null) {
            if (!isGeneric()) {
                typeDescriptor = Reference.getTypeDescriptor(this);
            } else {
                // TODO
                typeDescriptor = Reference.getTypeDescriptor(this) + "<";
                for (VariableType generic : genericList) {
                    typeDescriptor += Reference.getTypeDescriptor(generic);
                }
                typeDescriptor += ">";
            }
        }
        return typeDescriptor;
    }

    public String getReference() {
        if (typeReference == null) {
            typeReference = Reference.getReference(this);
        }
        return typeReference;
    }

    public List<String> getReferences() {
        if (typeReference == null) {
            typeReference = Reference.getReference(this);
        }
        List<String> res = new LinkedList<>();
        res.add(typeReference);
        if (isGeneric()) {
            genericList.forEach((item) -> {
                res.addAll(item.getReferences());
            });
        }
        return res;
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
        return type.equals(tmp.type);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.type);
        return hash;
    }
}
