package cn.ma.cei.xml;

import java.lang.reflect.Field;

public interface IXmlJsonConverter {
    void objectType(Object xmlObject);
    void doAttribute(Object xmlObject, Field item);
    void doObject(Object xmlObject, Field item);
    void doList(Object xmlObject, Field item);
}
