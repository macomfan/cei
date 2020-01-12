/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.environment;

import cn.ma.cei.utils.NormalMap;

/**
 *
 * @author u0151316
 */
public class Constant {

    private static final EnvironmentData<NormalMap<String, String>> requestMethod = new EnvironmentData<>(NormalMap::new);
    private static final EnvironmentData<NormalMap<String, String>> signatureMethod = new EnvironmentData<>(NormalMap::new);

    public static NormalMap<String, String> requestMethod() {
        return requestMethod.get();
    }

    public static NormalMap<String, String> signatureMethod() {
        return signatureMethod.get();
    }
}
