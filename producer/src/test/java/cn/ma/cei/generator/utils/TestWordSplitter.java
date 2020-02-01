package cn.ma.cei.generator.utils;

import cn.ma.cei.utils.WordSplitter;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TestWordSplitter {
    @Test
    public void test() {
        assertEquals("I18nTEST", WordSplitter.getUpperCamelCase("i18nTEST"));
        assertEquals("i18nTEST", WordSplitter.getLowerCamelCase("i18nTEST"));
        assertEquals("myNameIs", WordSplitter.getLowerCamelCase("MyNameIs"));
        assertEquals("myNameIs",WordSplitter.getLowerCamelCase("MyNameIs"));
        assertEquals("mynameIs",WordSplitter.getLowerCamelCase("MynameIs"));
        assertEquals("mynameIs",WordSplitter.getLowerCamelCase("MYNameIs"));
        assertEquals("mynameNNN",WordSplitter.getLowerCamelCase("MYNameNNN"));
        assertEquals("MYNameNNN",WordSplitter.getUpperCamelCase("MYNameNNN"));
        assertEquals("MyNameNNN",WordSplitter.getUpperCamelCase("myNameNNN"));
        assertEquals("LoooooooongWord",WordSplitter.getUpperCamelCase("loooooooongWord"));
        assertEquals("my_Name_Is", WordSplitter.getLowerCamelCase("MyNameIs", "_"));
        assertEquals("My_Name_Is", WordSplitter.getUpperCamelCase("myNameIs", "_"));
        assertEquals("TESTTYPEFLAG",WordSplitter.getUppercase("TestTypeFlag"));
        assertEquals("TEST_TYPE_FLAG",WordSplitter.getUppercase("TestTypeFlag", "_"));
        assertEquals("test_type_flag",WordSplitter.getLowercase("testTypeFlag", "_"));
        assertEquals("test_type_flag",WordSplitter.getLowercase("Test_Type_Flag"));
        assertEquals("Test_type_flag",WordSplitter.getUpperCamelCase("test_type_flag"));
        int aa = 0;
    }
}
