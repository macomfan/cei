/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.naming.IDescriptionConverter;

/**
 *
 * @author u0151316
 */
public abstract class Framework {
    public abstract String getFrameworkName();
    
    public abstract ExchangeBuilder getExchangeBuilder();
    
    public abstract IDescriptionConverter getDescriptionConverter();
}
