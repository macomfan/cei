/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.environment;

import cn.ma.cei.generator.builder.RestfulInterfaceBuilder;
import cn.ma.cei.generator.builder.SignatureBuilder;
import cn.ma.cei.utils.NormalMap;

/**
 *
 * @author u0151316
 */
public class Constant {

    private static final EnvironmentData<NormalMap<String, String>> requestMethod = new EnvironmentData<>();
    private static final EnvironmentData<NormalMap<String, String>> signatureMethod = new EnvironmentData<>();

    public static NormalMap<String, String> requestMethod() {
        if (requestMethod.isNull()) {
            requestMethod.trySet(new NormalMap<>());
        }
        return requestMethod.get();
    }

    public static NormalMap<String, String> signatureMethod() {
        if (signatureMethod.isNull()) {
            signatureMethod.trySet(new NormalMap<>());
        }
        return signatureMethod.get();
    }
}
