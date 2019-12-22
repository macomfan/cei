/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.database;

import cn.ma.cei.generator.builder.RestfulInterfaceBuilder;
import cn.ma.cei.utils.NormalMap;

/**
 *
 * @author u0151316
 */
public class Constant {
    private static final EnvironmentData<NormalMap<RestfulInterfaceBuilder.RequestMethod, String>> requestMethod = new EnvironmentData<>();
    
    public static NormalMap<RestfulInterfaceBuilder.RequestMethod, String> requestMethod() {
        if (requestMethod.isNull()) {
            requestMethod.trySet(new NormalMap<>());
        }
        return requestMethod.get();
    }
}
