package cn.ma.cei.sdk.impl.utils;

import cn.ma.cei.sdk.impl.RestfulRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class SignatureBuilder {
    public String combineString(String separator, List<String> strings) {
        String result = "";
        boolean isFirst = true;
        for (String item: strings) {
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

    /***
     * Convert current date/time to string
     * @param format The format of the output string, following below rules:
     *               %y  The 2 characters year code. 2019 is 19.
     *               %Y  The 4 characters year code. link 2019.
     *               %m  The 2 characters month in year. range is 01 - 12.
     *               %d  The 2 characters day in month. range is 01 - 31.
     *               %H  The 2 characters hour (24H) in day. range is 00 - 23
     *               %M  The 2 characters minute in hour. range is 00 - 59
     *               %S
     *               %s
     *               %US
     *               %Us
     *
     * @param gmtOffset
     * @return
     */
    public String getNow(String format, String gmtOffset) {
        String result = "";

        return result;
    }
}
