package cn.ma.cei.impl;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.impl.RestfulRequest;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SignatureTool {

    public enum Constant {
        ASC,
        DSC,
        HOST,
        METHOD,
        TARGET,
        UPPERCASE,
        LOWERCASE,
        NONE
    }

    public String escapeURL(String input) {
        try {
            return URLEncoder.encode(input, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException();
        }
    }

    public static String combineStringArray(List<String> list, String separator) {
        StringBuilder sb = new StringBuilder();
        list.forEach(item -> {
            if (sb.length() != 0) {
                sb.append(separator);
            }
            sb.append(item);
        });
        return sb.toString();
    }



    public static List<String> addStringArray(List<String> list, String value) {
        List<String> res = new LinkedList<>(list);
        res.add(value);
        return res;
    }






    public static String appendToString(String obj, String value) {
        return obj + value;
    }
    

}
