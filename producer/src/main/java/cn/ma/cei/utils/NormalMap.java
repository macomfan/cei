package cn.ma.cei.utils;

import cn.ma.cei.exception.CEIException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NormalMap<Key, Value> {
    private final Map<Key, Value> map = new HashMap<>();

    public Set<Key> keySet() {
        return map.keySet();
    }

    public Value get(Key key) {
        return map.get(key);
    }

    public Value tryGet(Key key) {
        Value res = map.get(key);
        if (res == null) {
            throw new CEIException("Cannot find in Map: " + key);
        }
        return res;
    }

    public void put(Key key, Value value) {
        map.put(key, value);
    }

    public void tryPut(Key key, Value value) {
        if (map.containsKey(key)) {
            throw new CEIException("Has existed in Map: " + key);
        }
        map.put(key, value);
    }

    public boolean containsKey(Key key) {
        return map.containsKey(key);
    }
}
