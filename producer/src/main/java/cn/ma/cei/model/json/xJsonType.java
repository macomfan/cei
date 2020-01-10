package cn.ma.cei.model.json;

import cn.ma.cei.model.base.xElement;
import cn.ma.cei.xml.XmlElementBase;

import javax.xml.bind.annotation.XmlAttribute;

@XmlElementBase
public abstract class xJsonType extends xElement {

    @XmlAttribute(name = "from")
    public String from;

    @XmlAttribute(name = "to")
    public String to;

    @XmlAttribute(name = "copy")
    public String copy;
}
