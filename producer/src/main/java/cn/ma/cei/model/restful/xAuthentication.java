package cn.ma.cei.model.restful;

import cn.ma.cei.model.base.xDataProcessorItem;
import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.processor.xAddQueryString;
import cn.ma.cei.model.processor.xCombineQueryString;
import cn.ma.cei.model.processor.xGetRequestInfo;
import cn.ma.cei.model.xProcedure;
import cn.ma.cei.xml.CEIXmlAnyElementTypes;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "authentication")
public class xAuthentication extends xElement {
    @XmlAttribute(name = "name")
    public String name;

    @XmlAttribute(name = "arguments")
    public String arguments;
}
