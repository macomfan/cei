/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cei.impl;

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
 * @author u0151316
 */
public class JsonWrapper {

    private JSONObject jsonObject = null;
    private JSONArray jsonArray = null;

    public static JsonWrapper parseFromString(String text) {
        try {
            Object tmp = JSON.parse(text);
            if (tmp instanceof JSONObject) {
                return new JsonWrapper((JSONObject) tmp);
            } else if (tmp instanceof JSONArray) {
                return new JsonWrapper((JSONArray) tmp);
            } else {
                throw new CEIException("Type error");
            }
        } catch (JSONException e) {
            CEILog.showFailure("[Json] Fail to parse json: %s", e.getMessage());
        } catch (Exception e) {
            CEILog.showFailure("[Json] Unknown error when parse: %s", e.getMessage());
        }
        return new JsonWrapper();
    }

    public JsonWrapper() {
    }

    /**
     * If the key is [x] return x, else return null.
     * If the key is [], it is used for add value to json array, the return will be -1.
     *
     * @param key the key.
     * @return the index or null.
     */
    private static Integer getIndexKey(String key) {
        if ("[]".equals(key)) {
            return -1;
        }
        String pattern = "^\\[[0-9]*]$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(key);
        if (m.find()) {
            return Integer.parseInt(key.substring(m.start() + 1, m.end() - 1));
        }
        return null;
    }

    public JsonWrapper(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JsonWrapper(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    private void initializeAsObject() {
        if (jsonObject == null && jsonArray == null) {
            jsonObject = new JSONObject();
        } else if (jsonArray != null) {
            CEILog.showFailure("[Json] Cannot create json object");
        }
    }

    private void initializeAsArray() {
        if (jsonObject == null && jsonArray == null) {
            jsonArray = new JSONArray();
        } else if (jsonObject != null) {
            CEILog.showFailure("[Json] Cannot create json array");
        }
    }

    public boolean contains(String key) {
        Integer index = getIndexKey(key);
        if (index != null && jsonArray != null) {
            return index < jsonArray.size() && index > 0;
        } else if (jsonObject != null) {
            return jsonObject.containsKey(key);
        }
        return false;
    }

    private void addJsonValue(String name, Object object) {
        Integer index = getIndexKey(name);
        if (index == null) {
            initializeAsObject();
            jsonObject.put(name, object);
        } else if (index == -1) {
            initializeAsArray();
            jsonArray.add(object);
        } else {
            CEILog.showFailure("[Json] Cannot specify thd detail index to insert a item to array");
        }
    }

    public void addJsonString(String key, String value) {
        addJsonValue(key, value);
    }

    public void addJsonNumber(String key, Long value) {
        addJsonValue(key, value);
    }

    public void addJsonBoolean(String key, Boolean value) {
        addJsonValue(key, value);
    }

    public void addJsonNumber(String key, BigDecimal value) {
        addJsonValue(key, value);
    }

    public void addJsonObject(String key, JsonWrapper jsonWrapper) {
        if (jsonWrapper.jsonObject != null) {
            addJsonValue(key, jsonWrapper.jsonObject);
        } else if (jsonWrapper.jsonArray != null) {
            addJsonValue(key, jsonWrapper.jsonArray);
        } else {
            CEILog.showFailure("[Json] Cannot add a null object to json object");
        }
    }

    private Object getByKey(String key) {
        Integer index = getIndexKey(key);
        if (index == null) {
            if (jsonObject != null) {
                return jsonObject.get(key);
            }
        } else {
            if (jsonArray != null && 0 <= index && index < jsonArray.size()) {
                return jsonArray.get(index);
            }
        }
        return null;
    }

    private <T> T checkMandatoryField(String key, T value) {
        if (value == null) {
            CEILog.showFailure("[Json] Get json item field: %s, does not exist", key);
        }
        return value;
    }

    private JsonWrapper checkMandatoryObject(String key, JsonWrapper value) {
        if (value == null) {
            CEILog.showFailure("[Json] The JsonWrapper is null");
        } else if (value.jsonArray == null && value.jsonObject == null) {
            CEILog.showFailure("[Json] Get json object: %s, does not exist", key);
        } else if (value.jsonObject != null && value.jsonArray != null) {
            CEILog.showFailure("[Json] The JsonWrapper is invalid", key);
        }
        return value;
    }

    @FunctionalInterface
    private interface Cast<T> {
        T castTo(Object value);
    }

    private <T> T caseTo(Object value, Cast<T> caseMethod, Class<T> cls) {
        try {
            return caseMethod.castTo(value);
        } catch (Exception e) {
            CEILog.showWarning("[Json] Failed to convert to %s", cls.getSimpleName());
            return null;
        }
    }

    public String getString(String key) {
        String value = getStringOrNull(key);
        return checkMandatoryField(key, value);
    }

    public String getStringOrNull(String key) {
        Object value = getByKey(key);
        return caseTo(value, TypeUtils::castToString, String.class);
    }

    public Long getLong(String key) {
        Long value = getLongOrNull(key);
        return checkMandatoryField(key, value);
    }

    public Long getLongOrNull(String key) {
        Object value = getByKey(key);
        return caseTo(value, TypeUtils::castToLong, Long.class);
    }

    public BigDecimal getDecimalOrNull(String key) {
        Object value = getByKey(key);
        return caseTo(value, TypeUtils::castToBigDecimal, BigDecimal.class);
    }

    public BigDecimal getDecimal(String key) {
        BigDecimal value = getDecimalOrNull(key);
        return checkMandatoryField(key, value);
    }

    public Boolean getBooleanOrNull(String key) {
        Object value = getByKey(key);
        return caseTo(value, TypeUtils::castToBoolean, Boolean.class);
    }

    public Boolean getBoolean(String key) {
        Boolean value = getBooleanOrNull(key);
        return checkMandatoryField(key, value);
    }

    public JsonWrapper getObject(String key) {
        JsonWrapper value = getObjectOrNull(key);
        return checkMandatoryObject(key, value);
    }

    public JsonWrapper getObjectOrNull(String key) {
        Object obj = getByKey(key);
        if (obj == null) {
            return new JsonWrapper();
        }
        if (obj instanceof JSONObject) {
            return new JsonWrapper((JSONObject) obj);
        } else if (obj instanceof JSONArray) {
            return new JsonWrapper((JSONArray) obj);
        } else {
            CEILog.showWarning("[Json] Failed to get Object, type error");
            return new JsonWrapper();
        }
    }

    public JsonWrapper getArray(String key) {
        JsonWrapper value = getObjectOrNull(key);
        return checkMandatoryObject(key, value);
    }

    public JsonWrapper getArrayOrNull(String key) {
        Object obj = getByKey(key);
        if (obj instanceof JSONArray) {
            return new JsonWrapper((JSONArray) obj);
        }
        return new JsonWrapper();
    }

    @FunctionalInterface
    public interface ForEachHandler {
        void process(JsonWrapper item);
    }

    public void forEach(ForEachHandler handler) {
        if (jsonObject != null || jsonArray == null) {
            return;
        }
        jsonArray.forEach(item -> {
            if (item instanceof JSONObject) {
                handler.process(new JsonWrapper((JSONObject) item));
            } else if (item instanceof JSONArray) {
                handler.process(new JsonWrapper((JSONArray) item));
            } else {
                handler.process(new JsonWrapper());
            }
        });
    }

    private <T> List<T> getArrayByKey(String key, Cast<T> castMethod, Class<T> cls) {
        Object obj = getByKey(key);
        if (!(obj instanceof JSONArray)) {
            return null;
        }
        List<T> res = new LinkedList<>();
        JSONArray array = (JSONArray) obj;
        array.forEach((item) -> res.add(castMethod.castTo(item)));
        return res;
    }

    public List<String> getStringArray(String key) {
        List<String> value = getStringArrayOrNull(key);
        return checkMandatoryField(key, value);
    }

    public List<String> getStringArrayOrNull(String key) {
        return getArrayByKey(key, TypeUtils::castToString, String.class);
    }

    public List<Long> getLongArray(String key) {
        List<Long> value = getLongArrayOrNull(key);
        return checkMandatoryField(key, value);
    }

    public List<Long> getLongArrayOrNull(String key) {
        return getArrayByKey(key, TypeUtils::castToLong, Long.class);
    }

    public List<BigDecimal> getDecimalArray(String key) {
        List<BigDecimal> value = getDecimalArrayOrNull(key);
        return checkMandatoryField(key, value);
    }

    public List<BigDecimal> getDecimalArrayOrNull(String key) {
        return getArrayByKey(key, TypeUtils::castToBigDecimal, BigDecimal.class);
    }

    public List<Boolean> getBooleanArray(String key) {
        List<Boolean> value = getBooleanArrayOrNull(key);
        return checkMandatoryField(key, value);
    }

    public List<Boolean> getBooleanArrayOrNull(String key) {
        return getArrayByKey(key, TypeUtils::castToBoolean, Boolean.class);
    }


    public byte[] toBytes() {
        return JSON.toJSONBytes(jsonObject);
    }

    public String toJsonString() {
        if (jsonObject != null) {
            return JSON.toJSONString(jsonObject);
        } else if (jsonArray != null){
            return JSON.toJSONString(jsonArray);
        }
        return "";
    }
}
