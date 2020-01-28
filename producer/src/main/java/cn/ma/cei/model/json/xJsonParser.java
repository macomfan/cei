package cn.ma.cei.model.json;

import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.json.checker.xJsonChecker;
import cn.ma.cei.xml.XmlAnyElementTypes;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "json_parser")
public class xJsonParser extends xElement {

    @XmlAnyElement(lax = true)
    @XmlAnyElementTypes({
            xJsonAuto.class,
            xJsonString.class,
            xJsonInteger.class,
            xJsonBoolean.class,
            xJsonDecimal.class,
            xJsonObjectArray.class,
            xJsonObject.class,
            xJsonStringArray.class,
            xJsonArray.class,
            xJsonChecker.class})
    public List<xJsonType> itemList;
}
