/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.golang;

import cn.ma.cei.generator.builder.ExchangeBuilder;
import cn.ma.cei.generator.builder.Framework;
import cn.ma.cei.generator.naming.IDescriptionConverter;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author U0151316
 */
public class GoFramework extends Framework {

    @Override
    public String getFrameworkName() {
        return "cei_golang";
    }

    @Override
    public ExchangeBuilder getExchangeBuilder() {
        return new GoExchangeBuilder();
    }

    @Override
    public IDescriptionConverter getDescriptionConverter() {
        return new GoDescriptionConverter();
    }
    
}
