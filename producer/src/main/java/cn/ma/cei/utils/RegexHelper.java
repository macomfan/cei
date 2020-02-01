package cn.ma.cei.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {
    public static String isReference(String testString) {
        String pattern = "^\\{.*}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(testString);
        if (m.find()) {
            return testString.substring(m.start() + 1, m.end() - 1);
        }
        return null;
    }

    public static List<String> findReference(String testString) {
        String pattern = "\\{.*?}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(testString);
        List<String> res = new LinkedList<>();
        while (m.find()) {
            res.add(testString.substring(m.start() + 1, m.end() - 1));
        }
        return res;
    }
}
