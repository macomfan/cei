package cn.ma.cei.finalizer;

import cn.ma.cei.utils.SecondLevelMap;
import cn.ma.cei.utils.UniqueList;

public class MapInterface<T> {

    // Exchange name - Client name - Interface name - Interface instance
    private SecondLevelMap<String, String, UniqueList<String, T>> map = new SecondLevelMap<>();

    public void clear() {
        map.clear();
    }
}
