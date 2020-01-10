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
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author u0151316
 */
public class JsonWrapper {

    private JSONObject jsonObject = null;
    private JSONArray jsonArray = null;

    public static JsonWrapper parseFromString(String text) {
        try {
            JSONObject jsonObject = (JSONObject) JSON.parse(text);
            if (jsonObject != null) {
                return new JsonWrapper(jsonObject);
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

    public void addJsonNumber(String name, Integer value) {
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

    private void checkMandatoryField(String name) {
        if (!containsKey(name)) {
            throw new CEIException("[Json] Get json item field: " + name + "does not exist");
        }
    }

    public boolean containsKey(String name) {
        shouldBeObject();
        return jsonObject.containsKey(name);
    }

    public String getString(String itemName) {
        checkMandatoryField(itemName);
        try {
            return jsonObject.getString(itemName);
        } catch (Exception e) {
            throw new CEIException("[Json] Get item error: " + itemName + " " + e.getMessage());
        }
    }

    public Integer getInteger(String itemName) {
        checkMandatoryField(itemName);
        try {
            return jsonObject.getInteger(itemName);
        } catch (Exception e) {
            throw new CEIException("[Json] Get item error: " + itemName + " " + e.getMessage());
        }
    }

    public Long getLong(String itemName) {
        checkMandatoryField(itemName);
        try {
            return jsonObject.getLong(itemName);
        } catch (Exception e) {
            throw new CEIException("[Json] Get item error: " + itemName + " " + e.getMessage());
        }
    }

    public BigDecimal getDecimal(String itemName) {
        checkMandatoryField(itemName);
        try {
            return new BigDecimal(jsonObject.getBigDecimal(itemName).stripTrailingZeros().toPlainString());
        } catch (Exception e) {
            throw new CEIException("[Json] Get item error: " + itemName + " " + e.getMessage());
        }
    }

    public Boolean getBoolean(String itemName) {
        checkMandatoryField(itemName);
        try {
            return jsonObject.getBoolean(itemName);
        } catch (Exception e) {
            throw new CEIException("[Json] Get item error: " + itemName + " " + e.getMessage());
        }
    }

    public JsonWrapper getArray(String itemName) {
        checkMandatoryField(itemName);
        try {
            JSONArray array = jsonObject.getJSONArray(itemName);
            return new JsonWrapper(array);
        } catch (Exception e) {
            throw new CEIException("[Json] Get array: " + itemName + " error");
        }
    }

    public JsonWrapper getObject(String itemName) {
        checkMandatoryField(itemName);
        try {
            JSONObject object = jsonObject.getJSONObject(itemName);
            return new JsonWrapper(object);
        } catch (Exception e) {
            throw new CEIException("[Json] Get object: " + itemName + " error");
        }
    }

    public List<JsonWrapper> getObjectArray(String itemName) {
        checkMandatoryField(itemName);
        List<JsonWrapper> res = new LinkedList<>();
        JSONArray array = jsonObject.getJSONArray(itemName);
        array.forEach((object) -> {
            if (!(object instanceof JSONObject)) {
                throw new CEIException("[Json] Parse array error in forEach");
            }
            res.add(new JsonWrapper((JSONObject) object));
        });
        return res;
    }

    public List<String> getStringArray(String itemName) {
        checkMandatoryField(itemName);
        List<String> res = new LinkedList<>();
        JSONArray array = jsonObject.getJSONArray(itemName);
        array.forEach((object) -> {
            if (!(object instanceof String)) {
                throw new CEIException("[Json] Parse array error in forEachAsString");
            }
            res.add((String) object);
        });
        return res;
    }

    public byte[] toBytes() {
        return JSON.toJSONBytes(jsonObject);
    }
}
