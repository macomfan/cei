####Json Wrapper Interface definition
```java
class JsonWrapper {
    static JsonWrapper parseFromString(String text);

    String getString(String key);
    Long getLong(String key);
    BigDecimal getDecimal(String key);
    Boolean getBoolean(String key);
    JsonWrapper getObject(String key);
    JsonWrapper getArray(String key);

    String getStringOrNull(String key);
    Long getLongOrNull(String key);
    BigDecimal getDecimalOrNull(String key);
    Boolean getBooleanOrNull(String key);
    JsonWrapper getObjectOrNull(String key);
    JsonWrapper getArrayOrNull(String key);
    
    List<String> getStringArray(String key);
    List<Long> getLongArray(String key);
    List<BigDecimal> getDecimalArray(String itemName);
    List<Long> getBooleanArray(String key);

    List<String> getStringArrayOrNull(String key);
    List<Long> getLongArrayOrNull(String key);
    List<BigDecimal> getDecimalArrayOrNull(String itemName);
    List<Long> getBooleanArrayOrNull(String key);

    void forEach(ForEachHandler handler);
    boolean contains(String key);
    String toJsonString();

    void addJsonString(String key, String value);
    void addJsonNumber(String key, Long value);
    void addJsonBoolean(String key, Boolean value);
    void addJsonNumber(String key, BigDecimal value);
    void addJsonObject(String key, JsonWrapper jsonWrapper);
}
```