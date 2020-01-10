package cn.ma.cei.utils;

import javafx.util.Pair;

public class MapWithValue2<Key, Value1, Value2> {
    private final NormalMap<Key, Pair<Value1, Value2>> valueMap = new NormalMap<>();

    public boolean containsKey(Key key) {
        return valueMap.containsKey(key);
    }
    
    public Value1 get1(Key key) {
        return valueMap.tryGet(key).getKey();
    }

    public Value2 get2(Key key) {
        return valueMap.tryGet(key).getValue();
    }

    public void put(Key key, Value1 value1, Value2 value2) {
        valueMap.put(key, new Pair<>(value1, value2));
    }

    public void tryPut(Key key, Value1 value1, Value2 value2) {
        valueMap.tryPut(key, new Pair<>(value1, value2));
    }
}