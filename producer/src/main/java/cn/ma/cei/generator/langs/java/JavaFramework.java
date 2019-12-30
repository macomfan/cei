/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.builder.Framework;
import cn.ma.cei.generator.builder.ExchangeBuilder;
import cn.ma.cei.generator.naming.IDescriptionConverter;

/**
 *
 * @author u0151316
 */
public class JavaFramework extends Framework{

    @Override
    public String getFrameworkName() {
        return "cei_java";
    }

    @Override
    public ExchangeBuilder getExchangeBuilder() {
        return new JavaExchangeBuilder();
    }

    @Override
    public IDescriptionConverter getDescriptionConverter() {
        return new JavaDescriptionConverter();
    }
    
}
