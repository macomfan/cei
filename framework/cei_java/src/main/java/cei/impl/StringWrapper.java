package cei.impl;

import java.util.LinkedList;
import java.util.List;

public class StringWrapper {
    private final List<String> items = new LinkedList<>();
    private final StringBuilder result = new StringBuilder();

    public void addStringArray(List<String> stringArray, boolean trim) {
        if (stringArray == null) {
            return;
        }
        if (trim) {
            stringArray.forEach(item -> {items.add(item.trim());});
        } else {
            items.addAll(stringArray);
        }
    }

    public void appendStringItem(String item) {
        items.add(item);
    }

    public void combineStringItems(String prefix, String suffix, String separator) {
        result.setLength(0);
        items.forEach(item -> {
            if (result.length() != 0) {
                result.append(separator);
            }
            result.append(prefix + item + suffix);
        });
    }

    public String toNormalString() {
        return result.toString();
    }
}
