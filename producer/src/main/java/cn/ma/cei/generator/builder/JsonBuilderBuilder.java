/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.environment.Variable;

/**
 *
 * @author u0151316
 */
public abstract class JsonBuilderBuilder {

    public abstract void defineRootJsonObject(Variable jsonObject);
    
    public abstract void addJsonString(Variable from, Variable jsonObject, Variable itemName);
    
    public abstract void addJsonNumber(Variable from, Variable jsonObject, Variable itemName);
    
    public abstract void addJsonBoolean(Variable from, Variable jsonObject, Variable itemName);

//    public abstract void addJsonObject(Variable from, Variable jsonObject, Variable itemName);
//
//    public abstract void addJsonObjectArray(Variable from, Variable jsonObject, Variable itemName);
//
//    public abstract void addJsonStringArray(Variable from, Variable jsonObject, Variable itemName);
//
//    public abstract void addJsonIntegerArray(Variable from, Variable jsonObject, Variable itemName);
//
//    public abstract void addJsonDecimalArray(Variable from, Variable jsonObject, Variable itemName);
//
//    public abstract void addJsonBooleanArray(Variable from, Variable jsonObject, Variable itemName);
}
