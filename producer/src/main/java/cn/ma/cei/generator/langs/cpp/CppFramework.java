/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.cpp;

import cn.ma.cei.generator.builder.ExchangeBuilder;
import cn.ma.cei.generator.builder.Framework;
import cn.ma.cei.generator.naming.IDescriptionConverter;
import java.util.Set;

/**
 *
 * @author u0151316
 */
public class CppFramework extends Framework{

    @Override
    public String getFrameworkName() {
        return "cei_cpp";
    }

    @Override
    public ExchangeBuilder getExchangeBuilder() {
        return new CppExchangeBuilder();
    }

    @Override
    public IDescriptionConverter getDescriptionConverter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<String> getKeywords() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
