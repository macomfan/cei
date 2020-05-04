package cn.ma.cei.impl;

import java.util.LinkedList;
import java.util.List;

public class StringWrapper {
    private final List<String> items = new LinkedList<>();
    private final StringBuilder result = new StringBuilder();

    public void appendStringItem(String item) {
        items.add(item);
    }

    public void combineStringItems(String separator) {
        result.setLength(0);
        items.forEach(item -> {
            if (result.length() != 0) {
                result.append(separator);
            }
            result.append(item);
        });
    }

    public String toNormalString() {
        return result.toString();
    }
}
