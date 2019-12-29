/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.environment.Variable;

/**
 *
 * @author U0151316
 */
public abstract class SignatureBuilder extends MethodBuilder {

    public abstract void getNow(Variable output, String format);
    
    public abstract void appendQueryStringByHardcode(Variable requestVariable, String key, String value);
    
    public abstract void appendQueryStringByVariable(Variable requestVariable, String key, Variable value);
    
    public abstract void combineQueryString(Variable requestVariable, Variable output, String sort, String separator);
    
    public abstract void getMethod(Variable requestVariable, Variable output, String convert);

}
