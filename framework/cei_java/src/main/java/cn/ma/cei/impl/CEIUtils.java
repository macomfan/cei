package cn.ma.cei.impl;

import cn.ma.cei.exception.CEIException;
import javafx.util.Pair;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CEIUtils {
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


    private static String processSingleTimeFormatSyntax(String input) {
        if (input == null || input.isEmpty() || input.charAt(0) != '%') {
            return null;
        }
        StringBuilder res = new StringBuilder();
        String commandString = "%";
        for (int i = 1; i < input.length(); i++) {
            if (!commandString.isEmpty()) {
                commandString += input.charAt(i);
                switch (commandString) {
                    case "%Y":
                        res.append("yyyy");
                        commandString = "";
                        res.append('\'');
                        break;
                    case "%M":
                        res.append("MM");
                        commandString = "";
                        res.append('\'');
                        break;
                    case "%D":
                        res.append("dd");
                        commandString = "";
                        res.append('\'');
                        break;
                    case "%h":
                        res.append("HH");
                        commandString = "";
                        res.append('\'');
                        break;
                    case "%m":
                        res.append("mm");
                        commandString = "";
                        res.append('\'');
                        break;
                    case "%s":
                        res.append("ss");
                        commandString = "";
                        res.append('\'');
                        break;
                    case "%ms":
                        res.append("SSS");
                        commandString = "";
                        res.append('\'');
                }
            } else {
                res.append(input.charAt(i));
            }
        }
        if (!commandString.isEmpty()) {
            // TODO
            // Cannot process
            System.err.println("Cannot support " + commandString);
        }
        else {
            res.append('\'');
        }
        return res.toString();
    }

    /**
     * *
     * Convert current date/time in UTC to string
     *
     * @param format The format of the output string, following below rules:
     * %Y The 4 characters year code. like 2019.
     * %M The 2 characters month in year. range is 01 - 12.
     * %D The 2 characters day in month. range is 01 - 31.
     * %h The 2 characters hour (24H) in day. range is 00 - 23.
     * %m The 2 characters minute in hour. range is 00- 59.
     * %s The 2 characters second in minute. range is 00 - 59.
     * %ms The 3 characters millisecond. range is 000 - 999.
     * Unix_s The unix format timestamp based on second.
     * Unix_ms The unix format timestamp based on millisecond. (default)
     *
     * @return The time string.
     */
    public static String getNow(String format) {
        if (format == null || format.isEmpty()) {
            return Long.toString(System.currentTimeMillis() / 1000);
        }
        if (format.equals("Unix_s")) {
            return Long.toString(System.currentTimeMillis());
        } else if (format.equals("Unix_ms")) {
            return Long.toString(System.currentTimeMillis() / 1000);
        } else {
            StringBuilder javaTimeFormatSting = new StringBuilder();
            String[] items = format.split("\\%");
            for (int i = 0; i<items.length; i++) {
                if (i == 0) {
                    javaTimeFormatSting.append('\'');
                    javaTimeFormatSting.append(items[i]);
                    javaTimeFormatSting.append('\'');
                }
                else {
                    javaTimeFormatSting.append(CEIUtils.processSingleTimeFormatSyntax("%" + items[i]));
                }
            }
            String res = javaTimeFormatSting.toString();
            Date now=new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy':'MM':'dd'T'HH':'mm':'ss");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            return simpleDateFormat.format(now);
        }
    }

    public static String combineQueryString(RestfulRequest request, Constant sort, String separator) {
        List<Pair<String, String>> queryString = new LinkedList<>(request.getQueryString());
        if (sort != null) {
            switch (sort) {
                case ASC:
                    queryString.sort((o1, o2) -> {
                        return o1.getKey().compareTo(o2.getKey());
                    });
                    break;
                case DSC:
                    queryString.sort((o1, o2) -> {
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

    public static String getRequestInfo(RestfulRequest request, Constant method, Constant convert) {
        String res = "";
        switch (method) {
            case HOST:
                try {
                    URL url = new URL(request.getUrl());
                    res = url.getHost();
                } catch (Exception e) {
                }
                break;
            case TARGET:
                res = request.getTarget();
                break;
            case METHOD:
                res = request.getMethod().toString();
                break;
            default:
                throw new CEIException(method.toString());
        }
        switch (convert) {
            case UPPERCASE:
                return res.toUpperCase();
            case LOWERCASE:
                return res.toLowerCase();
            case NONE:
                return res;
            default:
                throw new CEIException(convert.toString());
        }
    }

    public static String stringReplace(String format, String ... args) {
        return "";
    }

    private static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new CEIException("[URL] UTF-8 encoding not supported!");
        }
    }

    public static byte[] hmacsha256(String input, String key) {
        try {
            if (key == null || "".equals(key)) {
                throw new CEIException("Key is null");
            }
            Mac hmacSha256;
            hmacSha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            hmacSha256.init(secKey);
            return hmacSha256.doFinal(input.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new CEIException("hmacsha256 error");
        }
    }

    public static String base64(byte[] input) {
        return Base64.getEncoder().encodeToString(input);
    }
}
