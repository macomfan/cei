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
public abstract class SignatureBuilder extends MethodBuilder {
    
    public abstract void newStringArray(Variable stringArray);

    public abstract void getNow(Variable output, Variable format);

    public abstract void addQueryString(Variable requestVariable, Variable key, Variable value);
    
    public abstract void appendToString(boolean needDefineNewOutput, Variable output, Variable input);

    public abstract void combineQueryString(Variable requestVariable, Variable output, Variable sort, Variable separator);

    public abstract void getRequestInfo(Variable requestVariable, Variable output, Variable info, Variable convert);
    
    public abstract void addStringArray(Variable output, Variable input);
    
    public abstract void combineStringArray(Variable output, Variable input, Variable separator);
    
    public abstract void base64(Variable output, Variable input);
    
    public abstract void hmacsha265(Variable output, Variable input, Variable key);

}
