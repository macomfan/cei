package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.exception.CEIException;
import cn.ma.cei.utils.NormalMap;
import cn.ma.cei.utils.SecondLevelMap;

public class EnvironmentData<Key, Value> {
    private final SecondLevelMap<String, Language, NormalMap<Key, Value>> data = new SecondLevelMap<>();

    public boolean containsKey(Key key) {
        NormalMap<Key, Value> map = getMap(GlobalContext.getCurrentExchange(), GlobalContext.getCurrentLanguage());
        return map.containsKey(key);
    }

    public Value tryGet(Key key) {
        Value res = get(key);
        if (res == null) {
            CEIErrors.showCodeFailure(this.getClass(), "Cannot find key: %s", key);
        }
        return res;
    }

    public Value get(Key key) {
        NormalMap<Key, Value> map = getMap(GlobalContext.getCurrentExchange(), GlobalContext.getCurrentLanguage());
        if (!map.containsKey(key)) {
            return null;
        }
        return map.get(key);
    }

    public void tryPut(Key key, Value value) {
        NormalMap<Key, Value> map = getMap(GlobalContext.getCurrentExchange(), GlobalContext.getCurrentLanguage());
        if (map.containsKey(key)) {
            throw new CEIException("Cannot set duplicate value " + key);
        }
        map.put(key, value);
    }

    private boolean isNull() {
        return !data.containsKey(GlobalContext.getCurrentExchange(), GlobalContext.getCurrentLanguage());
    }

    private NormalMap<Key, Value> getMap(String exchange, Language language) {
        if (!data.containsKey(exchange, language)) {
            data.tryPut(exchange, language, new NormalMap<>());
        }
        return data.get(exchange, language);
    }
}
