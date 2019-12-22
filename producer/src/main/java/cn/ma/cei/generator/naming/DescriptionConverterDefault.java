package cn.ma.cei.generator.naming;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.utils.WordSplitter;

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
    public String getMethodDescriptor(String name) {
        checkInput(name);
        return WordSplitter.getUpperCamelCase(name);
    }

}
