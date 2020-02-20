/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.python3;

import cn.ma.cei.generator.Language;
import cn.ma.cei.generator.builder.IExchangeBuilder;
import cn.ma.cei.generator.builder.IFramework;
import cn.ma.cei.generator.naming.IDescriptionConverter;

/**
 *
 * @author U0151316
 */
public class Python3IFramework implements IFramework {

    @Override
    public Language getLanguage() {
        return new Language("python3", "cei_python3");
    }

    @Override
    public IExchangeBuilder getExchangeBuilder() {
        return new Python3IExchangeBuilder();
    }

    @Override
    public IDescriptionConverter getDescriptionConverter() {
        return new Python3DescriptionConverter();
    }
}
