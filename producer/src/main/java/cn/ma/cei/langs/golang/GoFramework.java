/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.golang;

import cn.ma.cei.generator.Language;
import cn.ma.cei.generator.builder.IExchangeBuilder;
import cn.ma.cei.generator.builder.IFramework;
import cn.ma.cei.generator.naming.IDescriptionConverter;

/**
 *
 * @author U0151316
 */
public class GoFramework implements IFramework {

    @Override
    public Language getLanguage() {
        return new Language("golang", "cei_golang");
    }

    @Override
    public IExchangeBuilder createExchangeBuilder() {
        return new GoExchangeBuilder();
    }

    @Override
    public IDescriptionConverter getDescriptionConverter() {
        return new GoDescriptionConverter();
    }
    
}
