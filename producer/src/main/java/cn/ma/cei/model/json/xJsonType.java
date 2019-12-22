package cn.ma.cei.model.json;

import cn.ma.cei.xml.XmlElementBase;

import javax.xml.bind.annotation.XmlAttribute;

@XmlElementBase
public abstract class xJsonType {
    @XmlAttribute(name = "from")
    public String from;

    @XmlAttribute(name = "to")
    public String to;
}
