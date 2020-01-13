package cn.ma.cei.generator.environment;

import cn.ma.cei.utils.NormalMap;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class VariableType {

    private final static EnvironmentData<NormalMap<String, String>> variableTypeDescriptor = new EnvironmentData<>(NormalMap::new);
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
        if (!typeName.equals("array")) {
            return false;
        }
        if (genericList.isEmpty()) {
            return false;
        }
        return VariableFactory.isCustomModel(genericList.get(0));
    }

    public String getDescriptor() {
        if (variableTypeDescriptor.get().containsKey(typeName)) {
            return variableTypeDescriptor.get().get(typeName);
        } else {
            String typeDescriptor = "";
            if (!isGeneric()) {
                typeDescriptor = VariableFactory.getModelTypeDescriptor(this);
            } else {
                String subTypeName = "";
                for (VariableType generic : genericList) {
                    if (generic != null) {
                        subTypeName += generic.getDescriptor();
                    }
                }
                typeDescriptor = VariableFactory.getModelTypeDescriptor(this)
                        + Environment.getCurrentDescriptionConverter().getGenericTypeDescriptor(subTypeName);
            }
            variableTypeDescriptor.get().put(typeName, typeDescriptor);
            return typeDescriptor;
        }
    }

    public String getReference() {
        return VariableFactory.getModelReference(this);
    }

    public List<String> getReferences() {
        String typeReference = VariableFactory.getModelReference(this);
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
