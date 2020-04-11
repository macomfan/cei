package cn.ma.cei.impl;

public class JsonChecker {
    private boolean result = true;

    public JsonChecker() {
    }

    public void checkEqual(String key, String value, JsonWrapper jsonWrapper) {
        String jsonValue = jsonWrapper.getString(key);
        if (!jsonValue.equals(value)) {
            result = false;
        }
    }

    public void checkNotEqual(String key, String value, JsonWrapper jsonWrapper) {
        String jsonValue = jsonWrapper.getString(key);
        if (jsonValue.equals(value)) {
            result = false;
        }
    }

    public boolean complete() {
        return result;
    }

    public void reportError() {

    }
}
