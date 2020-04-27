package cn.ma.cei.model.json;

import cn.ma.cei.xml.CEIXmlAnyElementTypes;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

public abstract class xJsonWithModel extends xJsonType {
    @XmlAttribute(name = "model")
    public String model;

    @XmlAnyElement(lax = true)
    @CEIXmlAnyElementTypes(classes = {
            xJsonString.class,
            xJsonInteger.class,
            xJsonBoolean.class,
            xJsonDecimal.class,
            xJsonObjectForEach.class,
            xJsonObject.class,
            xJsonStringArray.class,
            xJsonIntArray.class,
            xJsonDecimalArray.class,
            xJsonBooleanArray.class,
            xJsonValue.class,
            xJsonCheckerEqual.class,
            xJsonCheckerNotEqual.class})
    public List<xJsonType> itemList;
}
