package cn.ma.cei.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "query")
public class xQuery {
    @XmlAttribute(name = "name")
    public String name;

    @XmlAttribute(name = "value")
    public String value;
}
