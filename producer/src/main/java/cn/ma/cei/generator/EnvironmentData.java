/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator;

import cn.ma.cei.generator.GlobalContext;
import cn.ma.cei.generator.Language;
import cn.ma.cei.utils.SecondLevelMap;

/**
 *
 * @author u0151316
 */
class EnvironmentData<T> {
    
    private SecondLevelMap<String, Language, T> data = new SecondLevelMap<>();
    private Creation<T> creation = null;

    public EnvironmentData(Creation<T> creation) {
        this.creation = creation;
    }

    public EnvironmentData() {
    }

    @FunctionalInterface
    public interface Creation<T> {
        T create();
    }
    
    public T get() {
        if (isNull()) {
            data.tryPut(GlobalContext.getCurrentExchange(), GlobalContext.getCurrentLanguage(), creation.create());
        }
        return data.get(GlobalContext.getCurrentExchange(), GlobalContext.getCurrentLanguage());
    }

    public void trySet(T value) {
        data.tryPut(GlobalContext.getCurrentExchange(), GlobalContext.getCurrentLanguage(), value);
    }

    private boolean isNull() {
        return !data.containsKey(GlobalContext.getCurrentExchange(), GlobalContext.getCurrentLanguage());
    }
}
