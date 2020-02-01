package cn.ma.cei.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ReflectionHelper {
    public static List<Field> getAllFields(Object obj) {
        Class<?> cls = obj.getClass();
        List<Field> fieldList = new LinkedList<>();
        while (cls != null) {
            fieldList.addAll(Arrays.asList(cls.getDeclaredFields()));
            cls = cls.getSuperclass();
        }
        return fieldList;
    }

    public static Field getFieldByName(Object obj, String name) {
        Class<?> cls = obj.getClass();
        while (cls != null) {
            try {
                return cls.getDeclaredField(name);
            } catch (NoSuchFieldError | NoSuchFieldException ignored) {
            }
            cls = cls.getSuperclass();
        }
        return null;
    }
}
