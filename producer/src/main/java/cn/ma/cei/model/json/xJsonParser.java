package cn.ma.cei.model.json;

import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.json.checker.xJsonChecker;
import cn.ma.cei.xml.CEIXmlAnyElementTypes;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "json_parser")
public class xJsonParser extends xElement {

    @XmlAnyElement(lax = true)
    @CEIXmlAnyElementTypes({
            xJsonValue.class,
            xJsonString.class,
            xJsonInteger.class,
            xJsonBoolean.class,
            xJsonDecimal.class,
            xJsonObjectArray.class,
            xJsonObject.class,
            xJsonStringArray.class})
    public List<xJsonType> itemList;

    @XmlElement(name = "json_checker")
    public xJsonChecker jsonChecker;
}
