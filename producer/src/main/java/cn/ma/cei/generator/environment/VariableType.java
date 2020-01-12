package cn.ma.cei.generator.environment;

import cn.ma.cei.utils.NormalMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class VariableType {

    private final static EnvironmentData<NormalMap<String, String>> variableTypeDescriptor = new EnvironmentData<>();
    private final String typeName;
    private final List<VariableType> genericList = new LinkedList<>();
    private String typeDescriptor = null;
    private String typeReference = null;

    public VariableType(String typeName, VariableType... subTypes) {
        if (subTypes != null && subTypes.length != 0) {
            for (VariableType generic : subTypes) {
                genericList.add(generic);
            }
        }
        this.typeName = typeName;
    }

    public boolean isGeneric() {
        return !genericList.isEmpty();
    }

    public List<VariableType> getGenericList() {
        List<VariableType> res = new LinkedList<>();
        res.addAll(genericList);
        return res;
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

    public boolean isObject() {
        return VariableFactory.isModel(this);
    }

    public boolean isObjectList() {
        if (!typeName.equals("array")) {
            return false;
        }
        if (genericList.isEmpty()) {
            return false;
        }
        return VariableFactory.isModel(genericList.get(0));
    }

    public String getDescriptor() {
        if (typeDescriptor == null) {
            if (!isGeneric()) {
                typeDescriptor = Reference.getTypeDescriptor(this);
            } else {
                String subTypeName = "";
                for (VariableType generic : genericList) {
                    if (generic != null) {
                        subTypeName += generic.getDescriptor();
                    }
                }
                typeDescriptor = Reference.getTypeDescriptor(this)
                        + Environment.getCurrentDescriptionConverter().getGenericTypeDescriptor(subTypeName);
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
                if (item != null) {
                    res.addAll(item.getReferences());
                }
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
        return typeName.equals(tmp.typeName);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.typeName);
        return hash;
    }
}
