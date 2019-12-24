/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.cpp;

import cn.ma.cei.generator.builder.ExchangeBuilder;
import cn.ma.cei.generator.builder.Framework;

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
    
}
