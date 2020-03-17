package cn.ma.cei.model.restful;

import cn.ma.cei.model.authentication.*;
import cn.ma.cei.model.base.xAuthenticationItem;
import cn.ma.cei.model.base.xElement;
import cn.ma.cei.xml.CEIXmlAnyElementTypes;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

public class xAuthentication extends xElement {
    @XmlAttribute(name = "name")
    public String name;

    @XmlAnyElement(lax = true)
    @CEIXmlAnyElementTypes({
            xGetNow.class,
            xAddQueryString.class,
            xCombineQueryString.class,
            xGetRequestInfo.class,
            xAddStringArray.class,
            xAppendToString.class,
            xCombineStringArray.class,
            xBase64.class,
            xHmacsha256.class})
    public List<xAuthenticationItem> items;
}
