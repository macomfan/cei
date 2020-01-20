package cn.ma.cei.finalizer;

import cn.ma.cei.model.types.*;
import cn.ma.cei.model.*;

import java.util.HashMap;
import java.util.Map;

public class TypeAlias {

    private static Map<String, String> typeMap = new HashMap<>();

    static {
        registerType(xString.class, "String");
        registerType(xBoolean.class, "Boolean");
        registerType(xInt.class, "Integer");
        registerType(xLong.class, "Long");
        registerType(xDecimal.class, "Decimal");
        registerType(xStringArray.class, "StringArray");
        registerType(xBooleanArray.class, "BooleanArray");
        registerType(xIntArray.class, "IntArray");
        registerType(xLongArray.class, "LongArray");
        registerType(xDecimalArray.class, "DecimalArray");
        registerType(xObject.class, "Model");
        registerType(xObjectArray.class, "ModelArray");
        registerType(xModel.class, "Model");
    }

    private static void registerType(Class<?> cls, String alias) {
        typeMap.put(cls.getName(), alias);
    }

    public static String getClassNameByAlias(String alias) {
        if (!typeMap.containsValue(alias)) {
            return alias;
        }
        for (Map.Entry<String, String> entry : typeMap.entrySet()) {
            if (entry.getValue().equals(alias)) {
                return entry.getKey();
            }
        }
        return alias;
    }

    public static String getAliasByClassName(Class<?> cls) {
        if (typeMap.containsKey(cls.getName())) {
            return typeMap.get(cls.getName());
        }
        return cls.getName();
    }
}
