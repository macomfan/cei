package cei.impl;

import java.math.BigDecimal;

public class TypeConverter {
    public static String toNormalString(byte[] bytes) {
        return new String(bytes);
    }

    public static String toNormalString(Long value) {
        return value.toString();
    }

    public static String toNormalString(BigDecimal value) {
        return value.toPlainString();
    }

    public static String toNormalString(Boolean value) {
        return value.toString();
    }
}
