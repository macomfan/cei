/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.Variable;

/**
 *
 * @author U0151316
 */
public interface IAuthenticationBuilder extends IMethodBuilder {

    void newStringArray(Variable stringArray);


    
    void appendToString(boolean needDefineNewOutput, Variable output, Variable input);




    
    void addStringArray(Variable output, Variable input);
    
    void combineStringArray(Variable output, Variable input, Variable separator);


}
