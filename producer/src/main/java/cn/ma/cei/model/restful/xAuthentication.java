package cn.ma.cei.model.restful;

import cn.ma.cei.model.authentication.*;
import cn.ma.cei.model.base.xDataProcessorItem;
import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.processor.xBase64;
import cn.ma.cei.model.processor.xGetNow;
import cn.ma.cei.model.processor.xHmacSHA256;
import cn.ma.cei.xml.CEIXmlAnyElementTypes;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "authentication")
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
            xHmacSHA256.class})
    public List<xDataProcessorItem> items;
}
