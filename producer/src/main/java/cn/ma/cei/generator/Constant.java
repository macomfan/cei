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
    private static final EnvironmentData<String, String> requestInfo = new EnvironmentData<>();
    private static final EnvironmentData<String, String> keyword = new EnvironmentData<>();

    public static EnvironmentData<String, String> requestMethod() {
        return requestMethod;
    }

    public static EnvironmentData<String, String> keyword() {
        return keyword;
    }

    public static EnvironmentData<String, String> requestInfo() {
        return requestInfo;
    }
}
