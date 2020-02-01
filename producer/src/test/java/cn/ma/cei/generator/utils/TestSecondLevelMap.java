package cn.ma.cei.generator.utils;

import cn.ma.cei.utils.SecondLevelMap;
import org.junit.Assert;
import org.junit.Test;

public class TestSecondLevelMap {
    @Test
    public void test() {
        SecondLevelMap<String, String, Integer> testMap = new SecondLevelMap<>();
        testMap.put("1", "1.1", 1);
        Assert.assertEquals(1, testMap.get("1", "1.1").intValue());
        testMap.put("1", "1.1", 2);
        Assert.assertEquals(2, testMap.get("1", "1.1").intValue());
    }
}
