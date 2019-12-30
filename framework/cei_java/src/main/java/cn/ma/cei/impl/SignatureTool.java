package cn.ma.cei.impl;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.impl.RestfulRequest;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;

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

    public String combineString(String separator, List<String> strings) {
        String result = "";
        boolean isFirst = true;
        for (String item : strings) {
            if (!isFirst) {
                result += separator;
            }
            result += item;
            isFirst = false;
        }
        return result;
    }

//    public List<String> getQueryStringList(RestfulRequest request) {
//
//    }
//    public List<String> SortList(List<String> inputList) {
//
//    }
    public String escapeURL(String input) {
        try {
            return URLEncoder.encode(input, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException();
        }
    }

    /**
     * *
     * Convert current date/time to string
     *
     * @param format The format of the output string, following below rules: %y
     * The 2 characters year code. 2019 is 19. %Y The 4 characters year code.
     * link 2019. %m The 2 characters month in year. range is 01 - 12. %d The 2
     * characters day in month. range is 01 - 31. %H The 2 characters hour (24H)
     * in day. range is 00 - 23 %M The 2 characters minute in hour. range is 00
     * - 59 %S %s %US %Us
     *
     * @param gmtOffset
     * @return
     */
    private static final DateTimeFormatter DT_FORMAT = DateTimeFormatter
            .ofPattern("uuuu-MM-dd'T'HH:mm:ss");
    private static final ZoneId ZONE_GMT = ZoneId.of("Z");

    public static String getNow(String format) {
        return Instant.ofEpochSecond(Instant.now().getEpochSecond()).atZone(ZONE_GMT).format(DT_FORMAT);
    }

    public static String combineQueryString(RestfulRequest request, Constant sort, String separator) {
        List<Pair<String, String>> queryString = new LinkedList<>();
        queryString.addAll(request.getQueryString());
        if (sort != null) {
            switch (sort) {
                case ASC:
                    Collections.sort(queryString, (o1, o2) -> {
                        return o1.getKey().compareTo(o2.getKey());
                    });
                    break;
                case DSC:
                    Collections.sort(queryString, (o1, o2) -> {
                        return -o1.getKey().compareTo(o2.getKey());
                    });
                    break;
                case NONE:
                    // Do nothing
                    break;
                default:
                    throw new CEIException(sort.toString());
            }
        }

        StringBuilder builder = new StringBuilder();
        queryString.forEach((item) -> {
            if (!("").equals(builder.toString())) {
                if (separator != null && !separator.equals("")) {
                    builder.append(separator);
                }
            }
            builder.append(item.getKey());
            builder.append("=");
            builder.append(urlEncode(item.getValue()));
        });
        return builder.toString();
    }

    private static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new CEIException("[URL] UTF-8 encoding not supported!");
        }
    }

    public static String getRequestInfo(RestfulRequest request, Constant method, Constant convert) {
        switch (method) {
            case HOST:
                try {
                    URL url = new URL(request.getUrl());
                    return url.getHost();
                } catch (Exception e) {
                }
                break;
            case TARGET:
                return request.getTarget();
            case METHOD:
                return request.getMethod().toString();
            default:
                break;
        }
        return "";
    }
}
