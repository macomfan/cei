/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.Language;
import cn.ma.cei.generator.naming.IDescriptionConverter;
import java.util.Set;

/**
 *
 * @author u0151316
 */
public abstract class Framework {
    /***
     * Return the language of the framework. e.g.
     * For java: cei_java, for python3: cei_python3, for cpp: cei_python3
     * 
     * @return The folder of specify language in cei/framework.
     */
    public abstract Language getLanguage();
    
    /***
     * To create the new ExchangeBuilder
     * 
     * @return the new ExchangeBuilder instance.
     */
    public abstract ExchangeBuilder getExchangeBuilder();
    
    public abstract IDescriptionConverter getDescriptionConverter();
}
