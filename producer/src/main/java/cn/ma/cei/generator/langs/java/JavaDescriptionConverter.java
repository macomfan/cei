/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.java;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.naming.IDescriptionConverter;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.WordSplitter;
import java.util.List;

/**
 *
 * @author u0151316
 */
public class JavaDescriptionConverter implements IDescriptionConverter {

    private void checkInput(String name) {
        if (Checker.isEmpty(name)) {
            throw new CEIException("Name is null in NameConverter");
        }
    }

    @Override
    public String getFileDescriptor(String name) {
        checkInput(name);
        return WordSplitter.getUpperCamelCase(name);
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
        return WordSplitter.getLowerCamelCase(name);
    }

    @Override
    public String toStringDescriptor(String name) {
        if (name != null) {
            String newName = name.replace("\\", "\\\\");
            return "\"" + newName + "\"";
        }
        else {
            throw new CEIException("Name is null in NameConverter");
        }
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

}
