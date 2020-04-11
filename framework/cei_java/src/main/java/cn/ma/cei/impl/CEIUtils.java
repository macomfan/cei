package cn.ma.cei.impl;

import cn.ma.cei.exception.CEIException;
import javafx.util.Pair;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
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


    /**
     * *
     * Convert current date/time in UTC to string
     *
     * @param format The format of the output string.
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
            Date now = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
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

    public static String stringReplace(String format, String... args) {
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
