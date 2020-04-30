package cn.ma.cei.model.restful;

import cn.ma.cei.model.base.xElement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "authentication")
public class xAuthentication extends xElement {
    @XmlAttribute(name = "name")
    public String name;

    @XmlAttribute(name = "arguments")
    public String arguments;
}
