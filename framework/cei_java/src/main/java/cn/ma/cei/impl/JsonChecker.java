package cn.ma.cei.impl;

public class JsonChecker {
    private JsonWrapper jsonWrapper;
    private boolean result = true;

    public JsonChecker(JsonWrapper jsonWrapper) {
        this.jsonWrapper = jsonWrapper;
    }

    public void checkEqual(String key, String value) {
        String jsonValue = jsonWrapper.getString(key);
        if (!jsonValue.equals(value)) {
            result = false;
        }
    }

    public void checkNotEqual(String key, String value) {
        String jsonValue = jsonWrapper.getString(key);
        if (jsonValue.equals(value)) {
            result = false;
        }
    }

    public boolean complete() {
        return result;
    }
}
