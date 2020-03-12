/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.utils;

import cn.ma.cei.exception.CEIException;

import java.lang.reflect.ParameterizedType;

/**
 *
 * @author u0151316
 */
public class Checker {

    public static boolean isEmpty(String value) {
        return (value == null || "".equals(value));
    }

    public static void isNull(Object obj, Class<?> currentCls, String objectName) {
        if (obj == null) {
            throw new CEIException("[" + currentCls.getSimpleName() + "] " + objectName + " is null.");
        }
    }
}
