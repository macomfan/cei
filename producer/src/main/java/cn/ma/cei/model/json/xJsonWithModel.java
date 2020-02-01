package cn.ma.cei.model.json;

import cn.ma.cei.xml.CEIXmlAnyElementTypes;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

public abstract class xJsonWithModel extends xJsonType {
    @XmlAttribute(name = "model")
    public String model;

    @XmlAnyElement(lax = true)
    @CEIXmlAnyElementTypes({
        xJsonAuto.class,
        xJsonString.class,
        xJsonInteger.class,
        xJsonBoolean.class,
        xJsonDecimal.class,
        xJsonObjectArray.class,
        xJsonObject.class,
        xJsonStringArray.class})
    public List<xJsonType> itemList;
}
