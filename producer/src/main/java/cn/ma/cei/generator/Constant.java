/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator;

/**
 *
 * @author u0151316
 */
public class Constant {

    private static final EnvironmentData<String, String> requestMethod = new EnvironmentData<>();
    private static final EnvironmentData<String, String> authenticationMethod = new EnvironmentData<>();

    public static EnvironmentData<String, String> requestMethod() {
        return requestMethod;
    }

    public static EnvironmentData<String, String> authenticationMethod() {
        return authenticationMethod;
    }
}
