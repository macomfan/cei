package cn.ma.cei.finalizer;

import cn.ma.cei.model.types.*;
import cn.ma.cei.model.*;
import cn.ma.cei.utils.DuplexMap;


public class TypeAlias {

    private static DuplexMap<String, String> typeMap = new DuplexMap<>();

    static {
        registerType(xString.class);
        registerType(xBoolean.class);
        registerType(xInt.class);
        registerType(xDecimal.class);
        registerType(xStringArray.class);
        registerType(xBooleanArray.class);
        registerType(xIntArray.class);
        registerType(xDecimalArray.class);
        registerType(xObject.class);
        registerType(xObjectArray.class);
        registerType(xModel.class);
    }

    private static void registerType(Class<?> cls) {
        if (cls.isAnnotationPresent(Alias.class)) {
            Alias alias = cls.getAnnotation(Alias.class);
            typeMap.put(cls.getName(), alias.name());
        }
    }

    public static String getClassNameByAlias(String alias) {
        String res = typeMap.getKey(alias);
        if (res == null) {
            return alias;
        }
        return res;
    }

    public static String getAliasByClassName(Class<?> cls) {
        String res = typeMap.getValue(cls.getName());
        if (res == null) {
            return cls.getName();
        }
        return res;
    }
}
