package cn.ma.cei.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ReflectionHelper {
    public static List<Field> getAllFields(Object obj) {
        Class<?> c = obj.getClass();
        List<Field> fieldList = new LinkedList<>();
        while (c != null) {
            fieldList.addAll(Arrays.asList(c.getDeclaredFields()));
            c = c.getSuperclass();
        }
        return fieldList;
    }
}
