package cn.ma.cei.utils;

import java.util.*;

public class WordSplitter {
    //Camel-Case
    //Camel-Case
    //All lowercase
    //All uppercase

    private static Map<String, String> camelCaseFirstLowercaseBuffer = new HashMap<>();
    private static Map<String, String> camelCaseFirstUppercaseBuffer = new HashMap<>();
    private static Map<String, String> uppercaseBuffer = new HashMap<>();
    private static Map<String, String> lowercaseBuffer = new HashMap<>();

    public static String getLowercase(String name, String separator) {
        if (lowercaseBuffer.containsKey(name + separator)) {
            return lowercaseBuffer.get(name + separator);
        }
        List<String> words = parseWord(name);
        String res = "";
        boolean isFirst = true;
        for (String w : words) {
            if (isFirst) {
                res += w.toLowerCase();
                isFirst = false;
            } else {
                res += separator + w.toLowerCase();
            }
        }
        lowercaseBuffer.put(name, res + separator);
        return res;
    }

    public static String getLowercase(String name) {
        return getLowercase(name, "");
    }

    public static String getUppercase(String name, String separator) {
        if (uppercaseBuffer.containsKey(name + separator)) {
            return uppercaseBuffer.get(name + separator);
        }
        List<String> words = parseWord(name);
        String res = "";
        boolean isFirst = true;
        for (String w : words) {
            if (isFirst) {
                res += w.toUpperCase();
                isFirst = false;
            } else {
                res += separator + w.toUpperCase();
            }
        }
        uppercaseBuffer.put(name, res + separator);
        return res;
    }

    public static String getUppercase(String name) {
        return getUppercase(name, "");
    }

    public static String getLowerCamelCase(String name, String separator) {
        if (camelCaseFirstLowercaseBuffer.containsKey(name + separator)) {
            return camelCaseFirstLowercaseBuffer.get(name + separator);
        }
        List<String> words = parseWord(name);
        String res = "";
        boolean isFirst = true;
        for (String w : words) {
            if (isFirst) {
                res += convertToFirstLowercase(w);
                isFirst = false;
            } else {
                res += separator + w;
            }
        }
        camelCaseFirstLowercaseBuffer.put(name, res + separator);
        return res;
    }

    public static String getLowerCamelCase(String name) {
        return getLowerCamelCase(name, "");
    }

    public static String getUpperCamelCase(String name, String separator) {
        if (camelCaseFirstUppercaseBuffer.containsKey(name + separator)) {
            return camelCaseFirstUppercaseBuffer.get(name + separator);
        }
        List<String> words = parseWord(name);
        String res = "";
        boolean isFirst = true;
        for (String w : words) {
            if (isFirst) {
                res += convertToFirstUppercase(w);
                isFirst = false;
            } else {
                res += separator + w;
            }
        }
        camelCaseFirstUppercaseBuffer.put(name + separator, res);
        return res;
    }

    public static String getUpperCamelCase(String name) {
        return getUpperCamelCase(name, "");
    }


    private static boolean isLowercaseChar(char ch) {
        return 'a' <= ch && ch <= 'z' ? true : false;
    }

    private static String convertToFirstLowercase(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        return str.toLowerCase();
    }

    private static String convertToFirstUppercase(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        if (isLowercaseChar(str.charAt(0))) {
            StringBuilder sb = new StringBuilder();
            sb.append(Character.toUpperCase(str.charAt(0)));
            sb.append(str.substring(1));
            return sb.toString();
        } else {
            return str;
        }
    }

    private static boolean isUppercaseChar(char ch) {
        return 'A' <= ch && ch <= 'Z';
    }

    private static List<String> parseWord(String name) {
        if (name.length() == 0) {
            return null;
        } else if (name.length() == 1) {
            List<String> res = new LinkedList<>();
            res.add(name);
            return res;
        } else if (name.indexOf('-') != -1) {
            return Arrays.asList(name.split("\\_"));
        } else {
            List<String> res = new LinkedList<>();
            int indexStart = 0;
            for (int i = 0; i < name.length(); i++) {
                if (i == name.length() - 1) {
                    res.add(name.substring(indexStart));
                } else {
                    char ch = name.charAt(i);
                    char ch1 = name.charAt(i + 1);
                    if (isLowercaseChar(ch) && isUppercaseChar(ch1)) {
                        res.add(name.substring(indexStart, i + 1));
                        indexStart = i + 1;
                    }
                }
            }
            return res;
        }
    }

}
