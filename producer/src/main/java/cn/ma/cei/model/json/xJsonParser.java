package cn.ma.cei.model.json;

import cn.ma.cei.model.base.xDataProcessorItem;
import cn.ma.cei.xml.CEIXmlAnyElementTypes;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "json_parser")
public class xJsonParser extends xDataProcessorItem {

    @XmlAttribute(name = "model")
    public String model;

    @XmlAttribute(name = "name")
    public String name;

    @XmlAttribute(name = "input")
    public String input;

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
            xJsonValue.class})
    public List<xJsonType> itemList;

    @XmlElement(name = "json_checker")
    public xJsonChecker jsonChecker;
}
