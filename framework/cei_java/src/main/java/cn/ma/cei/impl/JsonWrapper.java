/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.impl;

import cn.ma.cei.exception.CEIException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author u0151316
 */
public class JsonWrapper {

    private Integer getIndexKey(String key) {
        String pattern = "^\\[[0-9]*\\]$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(key);
        if (m.find()) {
            return Integer.parseInt(key.substring(m.start() + 1, m.end() - 1));
        }
        return null;
    }

    private JSONObject jsonObject = null;
    private JSONArray jsonArray = null;

    public static JsonWrapper parseFromString(String text) {
        try {
            Object tmp = JSON.parse(text);
            if (tmp instanceof JSONObject) {
                return new JsonWrapper((JSONObject)tmp);
            } else if (tmp instanceof JSONArray) {
                return new JsonWrapper((JSONArray)tmp);
            } else {
                throw new CEIException("[Json] Unknown error when parse: " + text);
            }
        } catch (JSONException e) {
            throw new CEIException("[Json] Fail to parse json: " + text);
        } catch (Exception e) {
            throw new CEIException("[Json] " + e.getMessage());
        }
    }

    public JsonWrapper() {
        this.jsonObject = new JSONObject();
    }

    public JsonWrapper(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JsonWrapper(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    private void shouldBeObject() {
        if (jsonObject == null || jsonArray != null) {
            throw new CEIException("[Json] Should be object");
        }
    }

    private void shouldBeArray() {
        if (jsonObject != null || jsonArray == null) {
            throw new CEIException("[Json] Should be array");
        }
    }

    public void addJsonString(String name, String value) {
        if (value == null) {
            return;
        }
        jsonObject.put(name, value);
    }

    public void addJsonNumber(String name, Long value) {
        if (value == null) {
            return;
        }
        jsonObject.put(name, value);
    }

    public void addJsonBoolean(String name, Boolean value) {
        if (value == null) {
            return;
        }
        jsonObject.put(name, value);
    }

    public void addJsonNumber(String name, BigDecimal value) {
        if (value == null) {
            return;
        }
        jsonObject.put(name, value);
    }

    private Object checkMandatoryField(String name) {
        Object obj = null;
        if (jsonObject != null) {
            obj = this.jsonObject.get(name);
        }
        else if (jsonArray != null) {
            Integer index = getIndexKey(name);
            if (index == null) {
                throw new CEIException("[Json] Cannot get: " + name + " from json array");
            }
            obj = jsonArray.get(index.intValue());
        }
        if (obj == null) {
            throw new CEIException("[Json] Get json item field: " + name + " does not exist");
        }
        return obj;
    }

    public String getString(String itemName) {
        Object obj = checkMandatoryField(itemName);
        try {
            return TypeUtils.castToString(obj);
        } catch (Exception e) {
            throw new CEIException("[Json] Get item error: " + itemName + " " + e.getMessage());
        }
    }

    public Long getLong(String itemName) {
        Object obj = checkMandatoryField(itemName);
        try {
            return TypeUtils.castToLong(obj);
        } catch (Exception e) {
            throw new CEIException("[Json] Get item error: " + itemName + " " + e.getMessage());
        }
    }

    public BigDecimal getDecimal(String itemName) {
        Object obj = checkMandatoryField(itemName);
        try {
            return TypeUtils.castToBigDecimal(obj);
        } catch (Exception e) {
            throw new CEIException("[Json] Get item error: " + itemName + " " + e.getMessage());
        }
    }

    public Boolean getBoolean(String itemName) {
        Object obj = checkMandatoryField(itemName);
        try {
            return TypeUtils.castToBoolean(obj);
        } catch (Exception e) {
            throw new CEIException("[Json] Get item error: " + itemName + " " + e.getMessage());
        }
    }

    public JsonWrapper getObject(String itemName) {
        Object obj = checkMandatoryField(itemName);
        try {
            if (obj instanceof JSONObject) {
                return new JsonWrapper((JSONObject)obj);
            } else if (obj instanceof JSONArray) {
                return new JsonWrapper((JSONArray)obj);
            } else {
                throw new CEIException("[Json] Get object: " + itemName + " error, it is neither object or array");
            }
        } catch (Exception e) {
            throw new CEIException("[Json] Get object: " + itemName + " error");
        }
    }

    @FunctionalInterface
    public interface ForEachHandler {
        void process(JsonWrapper item);
    }

    public void forEach(ForEachHandler handler) {
        shouldBeArray();
        jsonArray.forEach(item -> {
            if (item instanceof JSONObject) {
                handler.process(new JsonWrapper((JSONObject)item));
            } else if (item instanceof JSONArray) {
                handler.process(new JsonWrapper((JSONArray)item));
            } else {
                throw new CEIException("[Json] the item is neither object or array");
            }
        });
    }

    public List<String> getStringArray(String itemName) {
        Object obj = checkMandatoryField(itemName);
        if (!(obj instanceof JSONArray)) {
            throw new CEIException("[Json] Get array: " + itemName + " error");
        }
        List<String> res = new LinkedList<>();
        JSONArray array = (JSONArray)obj;
        array.forEach((object) -> {
            if (!(object instanceof String)) {
                throw new CEIException("[Json] Parse array error in forEachAsString");
            }
            res.add((String) object);
        });
        return res;
    }

    public List<BigDecimal> getDecimalArray(String itemName) {
        Object obj = checkMandatoryField(itemName);
        if (!(obj instanceof JSONArray)) {
            throw new CEIException("[Json] Get array: " + itemName + " error");
        }
        List<BigDecimal> res = new LinkedList<>();
        JSONArray array = (JSONArray)obj;
        array.forEach((object) -> {
            if (!(object instanceof BigDecimal)) {
                throw new CEIException("[Json] Parse array error in forEachAsBigDecimal");
            }
            res.add((BigDecimal) object);
        });
        return res;
    }

    public byte[] toBytes() {
        return JSON.toJSONBytes(jsonObject);
    }
    
    public String toJsonString() {
        return JSON.toJSONString(jsonObject);
    }
}
