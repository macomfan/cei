/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.database;

import cn.ma.cei.utils.SecondLevelMap;

/**
 *
 * @author u0151316
 */
public class EnvironmentData<T> {
    
    private SecondLevelMap<String, Environment.Language, T> data = new SecondLevelMap<>();

    public boolean isNull() {
        return !data.containsKey(Environment.getCurrentExchange(), Environment.getCurrentLanguage());
    }
    
    public T get() {
        return data.get(Environment.getCurrentExchange(), Environment.getCurrentLanguage());
    }

    public void trySet(T value) {
        data.tryPut(Environment.getCurrentExchange(), Environment.getCurrentLanguage(), value);
    }
}
