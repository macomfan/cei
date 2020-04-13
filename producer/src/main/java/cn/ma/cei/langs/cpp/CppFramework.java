/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.cpp;

import cn.ma.cei.generator.Language;
import cn.ma.cei.generator.builder.IExchangeBuilder;
import cn.ma.cei.generator.builder.IFramework;
import cn.ma.cei.generator.naming.IDescriptionConverter;

/**
 *
 * @author u0151316
 */
public class CppFramework implements IFramework {

    @Override
    public Language getLanguage() {
        return new Language("cpp", "cei_cpp");
    }

    @Override
    public IExchangeBuilder createExchangeBuilder() {
        return new CppExchangeBuilder();
    }

    @Override
    public IDescriptionConverter getDescriptionConverter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
