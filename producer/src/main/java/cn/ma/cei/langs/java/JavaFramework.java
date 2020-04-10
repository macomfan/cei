/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.java;

import cn.ma.cei.generator.Language;
import cn.ma.cei.generator.builder.IFramework;
import cn.ma.cei.generator.builder.IExchangeBuilder;
import cn.ma.cei.generator.naming.IDescriptionConverter;

/**
 *
 * @author u0151316
 */
public class JavaFramework implements IFramework {

    @Override
    public Language getLanguage() {
        return new Language("java", "cei_java");
    }

    @Override
    public IExchangeBuilder createExchangeBuilder() {
        return new JavaExchangeBuilder();
    }

    @Override
    public IDescriptionConverter getDescriptionConverter() {
        return new JavaDescriptionConverter();
    }
}
