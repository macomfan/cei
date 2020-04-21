package cn.ma.cei.impl;

public class JsonChecker {
    private int result = -1;

    public JsonChecker() {
    }

    private void pass() {
        if (result == -1) {
            result = 1;
        }
    }

    private void fail() {
        result = 0;
    }

    public void checkEqual(String key, String value, JsonWrapper jsonWrapper) {
        if (jsonWrapper.contains(key)) {
            String jsonValue = jsonWrapper.getString(key);
            if (jsonValue.equals(value)) {
                pass();
            }
        }
        else {
            fail();
        }
    }

    public void checkNotEqual(String key, String value, JsonWrapper jsonWrapper) {
        if (jsonWrapper.contains(key)) {
            String jsonValue = jsonWrapper.getString(key);
            if (!jsonValue.equals(value)) {
                pass();
            }
        }
    }

    public boolean complete() {
        return result == 1;
    }

    public void reportError() {

    }
}
