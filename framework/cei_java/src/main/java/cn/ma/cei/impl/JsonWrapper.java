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

/**
 *
 * @author u0151316
 */
public class JsonWrapper {

    static class JsonPathNode {

        public String path = "";
        public JsonPathNode next = null;
        public int arrayIndex = -1;
    }

    public static class JsonPath {

        private JsonWrapper currentJsonWrapper = null;
        private JsonPathNode rootPathNode = null;

        public JsonPath(JsonWrapper currentJsonWrapper, String path) {
            if (path == null || path.isEmpty()) {
                throw new CEIException("[JsonPath] Empty path");
            }
            try {
                String newPath = path;
                if (path.charAt(0) != '\\') {
                    newPath = "\\" + path;
                }
                String[] items = newPath.split("\\\\");
                JsonPathNode perJsonPathNode = null;
                for (int i = 1; i < items.length; i++) {
                    JsonPathNode newJsonPathNode = new JsonPathNode();
                    if (perJsonPathNode == null) {
                        rootPathNode = newJsonPathNode;
                    } else {
                        perJsonPathNode.next = newJsonPathNode;
                    }
                    if (items[i].indexOf('[') != -1) {
                        String index = items[i].replaceFirst("\\[", "");
                        index = index.replaceFirst("]", "");
                        newJsonPathNode.arrayIndex = Integer.parseInt(index);

                    } else {
                        newJsonPathNode.path = items[i];
                    }
                    perJsonPathNode = newJsonPathNode;
                }
                this.currentJsonWrapper = currentJsonWrapper;
            } catch (Exception e) {
                throw new CEIException("[JsonPath] Not invaild path: " + path);
            }
        }

        public Object get() {
            JsonPathNode curr = rootPathNode;
            Object currObject = null;
            if (currentJsonWrapper.jsonObject != null) {
                currObject = currentJsonWrapper.jsonObject;
            } else if (currentJsonWrapper.jsonArray != null) {
                currObject = currentJsonWrapper.jsonArray;
            }
            while (curr != null) {
                if (curr.arrayIndex != -1) {
                    //Get as array currJson should be array
                    assert currObject instanceof JSONArray;
                    JSONArray jsonArray = (JSONArray) currObject;
                    if (jsonArray.size() <= curr.arrayIndex) {
                        return null;
                    }
                    currObject = jsonArray.get(curr.arrayIndex);
                } else {
                    // Get as object currJson should be object
                    assert currObject instanceof JSONObject;
                    JSONObject jsonObject = (JSONObject) currObject;
                    if (!jsonObject.containsKey(curr.path)) {
                        return null;
                    }
                    currObject = jsonObject.get(curr.path);
                }
                curr = curr.next;
            }
            return currObject;
        }
    }

    private JSONObject jsonObject = null;
    private JSONArray jsonArray = null;

    public static JsonWrapper parseFromString(String text) {
        try {
            JSONObject jsonObject = (JSONObject) JSON.parse(text);
            // TODO maybe JSONArray
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

    private Object checkMandatoryField(String name) {
        Object obj = new JsonPath(this, name).get();
        if (obj == null) {
            throw new CEIException("[Json] Get json item field: " + name + "does not exist");
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

    public JsonWrapper getArray(String itemName) {
        Object obj = checkMandatoryField(itemName);
        try {
            return new JsonWrapper((JSONArray) obj);
        } catch (Exception e) {
            throw new CEIException("[Json] Get array: " + itemName + " error");
        }
    }

    public JsonWrapper getObject(String itemName) {
        Object obj = checkMandatoryField(itemName);
        try {
            return new JsonWrapper((JSONObject)obj);
        } catch (Exception e) {
            throw new CEIException("[Json] Get object: " + itemName + " error");
        }
    }

    public List<JsonWrapper> getObjectArray(String itemName) {
        Object obj = checkMandatoryField(itemName);
        if (!(obj instanceof JSONArray)) {
            throw new CEIException("[Json] Get array: " + itemName + " error");
        }
        List<JsonWrapper> res = new LinkedList<>();
        JSONArray array = (JSONArray) obj;
        array.forEach((object) -> {
            if (!(object instanceof JSONObject)) {
                throw new CEIException("[Json] Parse array error in forEach");
            }
            res.add(new JsonWrapper((JSONObject) object));
        });
        return res;
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

    public byte[] toBytes() {
        return JSON.toJSONBytes(jsonObject);
    }
    
    public String toJsonString() {
        return JSON.toJSONString(jsonObject);
    }
}
