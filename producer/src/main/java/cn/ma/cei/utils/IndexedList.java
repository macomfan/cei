package cn.ma.cei.utils;


import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.environment.Variable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class IndexedList<IndexType, ValueType> {
    private List<ValueType> indexedList = new LinkedList<>();
    private Map<IndexType, ValueType> indexed = new HashMap<>();

    public boolean containsKey(IndexType key) {
        return indexed.containsKey(key);
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
