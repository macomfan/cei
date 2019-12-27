package cn.ma.cei.model.json;

import cn.ma.cei.xml.XmlAnyElementTypes;
import cn.ma.cei.xml.XmlElementBase;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

@XmlElementBase
public abstract class xJsonWithModel extends xJsonType {
    @XmlAttribute(name = "model")
    public String model;

    @XmlAnyElement(lax = true)
    @XmlAnyElementTypes({
        xJsonAuto.class,
        xJsonString.class,
        xJsonInteger.class,
        xJsonLong.class,
        xJsonBoolean.class,
        xJsonDecimal.class,
        xJsonObjectArray.class,
        xJsonObject.class,
        xJsonStringArray.class})
    public List<xJsonType> itemList;
}
