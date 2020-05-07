package cn.ma.cei.langs.test;

import cn.ma.cei.generator.naming.IDescriptionConverter;

import java.util.List;
import java.util.Set;

public class testDescriptionConverter implements IDescriptionConverter {
    @Override
    public String getFileDescriptor(String name) {
        return name;
    }

    @Override
    public String getModelDescriptor(String name) {
        return name;
    }

    @Override
    public String getClientDescriptor(String name) {
        return name;
    }

    @Override
    public String getVariableDescriptor(String name) {
        return name;
    }

    @Override
    public String getMemberVariableDescriptor(String name) {
        return name;
    }

    @Override
    public String getPrivateMemberDescriptor(String name) {
        return name;
    }

    @Override
    public String getMethodDescriptor(String name) {
        return name;
    }

    @Override
    public String toStringDescriptor(String name) {
        return name;
    }

    @Override
    public String getSelfDescriptor() {
        return "this";
    }

    @Override
    public String getGenericTypeDescriptor(String baseName, List<String> subNames) {
        StringBuilder stringBuilder = new StringBuilder();
        subNames.forEach((item) -> {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(item);
        });
        stringBuilder.insert(0, baseName + "<");
        stringBuilder.append(">");
        return stringBuilder.toString();
    }

    @Override
    public Set<String> getKeywords() {
        return null;
    }

    @Override
    public Set<String> getBuildIn() {
        return null;
    }
}
