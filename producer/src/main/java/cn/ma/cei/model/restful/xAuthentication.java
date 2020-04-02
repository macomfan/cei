package cn.ma.cei.model.restful;

import cn.ma.cei.model.authentication.*;
import cn.ma.cei.model.base.xDataProcessorItem;
import cn.ma.cei.model.xProcedure;
import cn.ma.cei.xml.CEIXmlAnyElementTypes;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "authentication")
public class xAuthentication extends xDataProcessorItem {
    @XmlAttribute(name = "name")
    public String name;

    @XmlAnyElement(lax = true)
    @CEIXmlAnyElementTypes({
            xProcedure.class,
            xAddQueryString.class,
            xCombineQueryString.class,
            xGetRequestInfo.class,})
    public List<xDataProcessorItem> items;
}
