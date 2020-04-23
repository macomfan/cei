/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.utils;

import cn.ma.cei.exception.CEIErrorType;
import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.IBuilderBase;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 *
 * @author u0151316
 */
public class Checker {
    public static <T> T checkNull(T object, IBuilderBase builder, String description) {
        if (object == null) {
            CEIErrors.showCodeFailure(builder.getClass(), "%s is null.", description);
        } else {
            return object;
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
