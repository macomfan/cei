/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.python3;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.naming.IDescriptionConverter;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.WordSplitter;
import java.util.List;

/**
 *
 * @author u0151316
 */
public class Python3DescriptionConverter implements IDescriptionConverter {

    private void checkInput(String name) {
        if (Checker.isEmpty(name)) {
            throw new CEIException("Name is null in NameConverter");
        }
    }

    @Override
    public String getFileDescriptor(String name) {
        checkInput(name);
        return WordSplitter.getLowercase(name);
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
        return WordSplitter.getLowercase(name, "_");
    }

    @Override
    public String getMemberVariableDescriptor(String name) {
        checkInput(name);
        return WordSplitter.getLowercase(name, "_");
    }

    @Override
    public String getMethodDescriptor(String name) {
        checkInput(name);
        return WordSplitter.getLowercase(name, "_");
    }

    @Override
    public String toStringDescriptor(String name) {
        if (name != null) {
            return "\"" + name + "\"";
        } else {
            throw new CEIException("Name is null in NameConverter");
        }
    }

    @Override
    public String getGenericTypeDescriptor(String baseName, List<String> subNames) {
        return baseName;
    }
}
