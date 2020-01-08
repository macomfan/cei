package cn.ma.cei.utils;


import cn.ma.cei.exception.CEIException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UniquetList<IndexType, ValueType> {
    private final List<ValueType> indexedList = new LinkedList<>();
    private final Map<IndexType, ValueType> indexed = new HashMap<>();

    public boolean containsKey(IndexType key) {
        return indexed.containsKey(key);
    }

    public boolean isEmpty() {
        return indexedList.isEmpty();
    }
    
    public void put(IndexType key, ValueType value) {
        if (indexed.containsKey(key)) {
            throw new CEIException("[IndexedList] Cannot add duplicate element: " + key.toString());
        }
        indexed.put(key, value);
        indexedList.add(value);
    }

    public ValueType get(IndexType key) {
        if (indexed.containsKey(key)) {
            return indexed.get(key);
        }
        return null;
    }

    public List<ValueType> values() {
        List<ValueType> res = new LinkedList<>();
        res.addAll(indexedList);
        return res;
    }

}
