package cn.ma.cei.utils;

import java.util.HashMap;
import java.util.Map;

public class DuplexMap<Key, Value> {
    private final Map<Key, Value> keyValueMap = new HashMap<>();
    private final Map<Value, Key> valueKeyMap = new HashMap<>();

    public void put(Key key, Value value) {
        keyValueMap.put(key, value);
        valueKeyMap.put(value, key);
    }

    public Key getKey(Value value) {
        if (valueKeyMap.containsKey(value)) {
            return valueKeyMap.get(value);
        }
        return null;
    }

    public Value getValue(Key key) {
        if (keyValueMap.containsKey(key)) {
            return keyValueMap.get(key);
        }
        return null;
    }
}
