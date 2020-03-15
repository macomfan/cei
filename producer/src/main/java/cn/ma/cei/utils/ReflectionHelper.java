package cn.ma.cei.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ReflectionHelper {
    public static <T> T getFieldValue(Field field, Object obj, Class<T> cls) {
        try {
            return cls.cast(field.get(obj));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Field> getFields(Object obj) {
        if (obj == null) {
            return new LinkedList<>();
        }
        Class<?> cls = obj.getClass();
        return new LinkedList<>(Arrays.asList(cls.getDeclaredFields()));
    }

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
