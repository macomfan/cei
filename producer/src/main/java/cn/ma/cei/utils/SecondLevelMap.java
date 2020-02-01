package cn.ma.cei.utils;


public class SecondLevelMap<Level1, Level2, Value> {

    private NormalMap<Level1, NormalMap<Level2, Value>> map = new NormalMap<>();

    public Value tryGet(Level1 key1, Level2 key2) {
        NormalMap<Level2, Value> map2 = map.tryGet(key1);
        return map2.tryGet(key2);
    }

    public boolean containsKey1(Level1 key1) {
        return map.containsKey(key1);
    }

    public boolean containsKey(Level1 key1, Level2 key2) {
        if (!map.containsKey(key1)) {
            return false;
        }
        NormalMap<Level2, Value> map2 = map.get(key1);
        if (!map2.containsKey(key2)) {
            return false;
        }
        return true;
    }

    /***
     * Get the value by key1, key2
     *
     * @param key1
     * @param key2
     * @return The value by key1, key2, return null if the cannot find the value.
     */
    public Value get(Level1 key1, Level2 key2) {
        NormalMap<Level2, Value> map2 = map.get(key1);
        if (map2 == null) {
            return null;
        }
        return map2.tryGet(key2);
    }

    private NormalMap<Level2, Value> getOrCreateByLevel1(Level1 key1) {
        if (!map.containsKey(key1)) {
            map.put(key1, new NormalMap<>());
        }
        return map.tryGet(key1);
    }

    public void put(Level1 key1, Level2 key2, Value value) {
        NormalMap<Level2, Value> map2 = getOrCreateByLevel1(key1);
        map2.put(key2, value);
    }

    public void tryPut(Level1 key1, Level2 key2, Value value) {
        NormalMap<Level2, Value> map2 = getOrCreateByLevel1(key1);
        map2.tryPut(key2, value);
    }
}
