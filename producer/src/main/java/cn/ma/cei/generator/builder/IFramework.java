/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.Language;
import cn.ma.cei.generator.naming.IDescriptionConverter;

/**
 *
 * @author u0151316
 */
public interface IFramework extends IBuilderBase {
    /***
     * Return the language of the framework.
     * For java: new Language("java", "cei_java"),
     * For python3: new Language("python3", "cei_python3")
     * For cpp: new Language("cpp", "cei_python3")
     * 
     * @return The folder of specify language in cei/framework.
     */
    Language getLanguage();
    
    /***
     * To create the new ExchangeBuilder
     * 
     * @return the new ExchangeBuilder instance.
     */
    IExchangeBuilder createExchangeBuilder();
    
    IDescriptionConverter getDescriptionConverter();
}
