package cn.ma.cei.generator.naming;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.utils.WordSplitter;
import java.util.List;
import java.util.Set;

public class DescriptionConverterDefault implements IDescriptionConverter {
    private void checkInput(String name) {
        if (name == null || name.equals("")) {
            throw new CEIException("Name is null in NameConverter");
        }
    }

    @Override
    public String getModelDescriptor(String name) {
        checkInput(name);
        return WordSplitter.getUpperCamelCase(name);
    }

    @Override
    public String getClientDescriptor(String name) {
        checkInput(name);
        return WordSplitter.getUpperCamelCase(name);
    }

    @Override
    public String getVariableDescriptor(String name) {
        checkInput(name);
        return WordSplitter.getLowerCamelCase(name);
    }

    @Override
    public String getMemberVariableDescriptor(String name) {
        checkInput(name);
        return WordSplitter.getLowerCamelCase(name);
    }

    @Override
    public String getPrivateMemberDescriptor(String name) {
        checkInput(name);
        return WordSplitter.getLowerCamelCase(name);
    }

    @Override
    public String getMethodDescriptor(String name) {
        checkInput(name);
        return WordSplitter.getUpperCamelCase(name);
    }

    @Override
    public String getFileDescriptor(String name) {
        checkInput(name);
        return WordSplitter.getUpperCamelCase(name);
    }

    @Override
    public String toStringDescriptor(String name) {
        checkInput(name);
        return "\"" + name + "\"";
    }

    @Override
    public String getSelfDescriptor() {
        return "this";
    }

    @Override
    public String getGenericTypeDescriptor(String baseName, List<String> subNames) {
        return baseName;
    }

    @Override
    public Set<String> getKeywords() {
        return null;
    }

}
