/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.Variable;

/**
 * @author u0151316
 */
public interface IJsonBuilderBuilder {

    void defineRootJsonObject(Variable jsonObject);

    /**
     * For create new json object
     * e.g.
     * JsonWrapper jsonObject = new JsonWrapper()
     *
     * @param jsonObject
     */
    void defineJsonObject(Variable jsonObject);

    /**
     * Add string value to the jsonObject.
     * e.g.
     * jsonObject.addString(key, value)
     *
     * @param value      the value to be set to the json object
     * @param jsonObject the current json object
     * @param key        the string of key name
     */
    void addJsonString(Variable value, Variable jsonObject, Variable key);

    void addJsonDecimal(Variable value, Variable jsonObject, Variable key);

    void addJsonBoolean(Variable value, Variable jsonObject, Variable key);

    void addJsonInt(Variable value, Variable jsonObject, Variable key);

    //    public abstract void addJsonObject(Variable value, Variable jsonObject, Variable key);
//
//    public abstract void addJsonObjectArray(Variable value, Variable jsonObject, Variable key);
//
    void addJsonStringArray(Variable value, Variable jsonObject, Variable key);

    void addJsonIntegerArray(Variable value, Variable jsonObject, Variable key);

    void addJsonDecimalArray(Variable value, Variable jsonObject, Variable key);

    void addJsonBooleanArray(Variable value, Variable jsonObject, Variable key);

    /**
     * Add a json object to current json object.
     * e.g.
     * jsonObject.addJsonObject(key, value);
     *
     * @param value
     * @param key
     */
    void addJsonObject(Variable value, Variable jsonObject, Variable key);
}
