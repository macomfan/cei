/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.utils;

import cn.ma.cei.exception.CEIErrorType;
import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.exception.CEIException;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 *
 * @author u0151316
 */
public class Checker {

    public static <T> T checkBuilder(T builder, Class<?> creator, String description) {
        if (builder == null) {
            CEIErrors.showCodeFailure(creator, "%s is null.", description);
        } else {
            return builder;
        }
        return null;
    }

    public static boolean isEmpty(String value) {
        return (value == null || "".equals(value));
    }

    public static void isNull(Object obj, Class<?> currentCls, String objectName) {
        if (obj == null) {
            CEIErrors.showFailure(CEIErrorType.CODE, "[" + currentCls.getSimpleName() + "] " + objectName + " is null.");
        }
    }

    public static <T> boolean isNull(List<T> obj) {
        return obj == null || obj.size() == 0;
    }
}
