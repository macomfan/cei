package cn.ma.cei.utils;


import cn.ma.cei.exception.CEIException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UniqueList<IndexType, ValueType> {
    private final List<ValueType> valueList = new LinkedList<>();
    private final List<IndexType> indexList = new LinkedList<>();
    private final Map<IndexType, ValueType> indexed = new HashMap<>();

    public boolean containsKey(IndexType key) {
        return indexed.containsKey(key);
    }

    public boolean isEmpty() {
        return valueList.isEmpty();
    }
    
    public void put(IndexType key, ValueType value) {
        if (indexed.containsKey(key)) {
            throw new CEIException("[IndexedList] Cannot add duplicate element: " + key.toString());
        }
        indexList.add(key);
        indexed.put(key, value);
        valueList.add(value);
    }

    public void clear() {
        valueList.clear();
        indexed.clear();
        indexList.clear();
    }

    public ValueType get(IndexType key) {
        if (indexed.containsKey(key)) {
            return indexed.get(key);
        }
        return null;
    }

    public List<ValueType> values() {
        return new LinkedList<>(valueList);
    }

    public List<IndexType> indexs() {
        return new LinkedList<>(indexList);
    }
}
