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
public interface ISignatureBuilder extends IMethodBuilder {
    
    void newStringArray(Variable stringArray);

    void getNow(Variable output, Variable format);

    void addQueryString(Variable requestVariable, Variable key, Variable value);
    
    void appendToString(boolean needDefineNewOutput, Variable output, Variable input);

    void combineQueryString(Variable requestVariable, Variable output, Variable sort, Variable separator);

    void getRequestInfo(Variable requestVariable, Variable output, Variable info, Variable convert);
    
    void addStringArray(Variable output, Variable input);
    
    void combineStringArray(Variable output, Variable input, Variable separator);
    
    void base64(Variable output, Variable input);
    
    void hmacsha265(Variable output, Variable input, Variable key);

}
