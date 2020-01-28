package cn.ma.cei.model.json.checker;

import cn.ma.cei.model.base.xElement;
import cn.ma.cei.xml.XmlElementBase;

import javax.xml.bind.annotation.XmlAttribute;

@XmlElementBase
public abstract class xJsonCheckerItem extends xElement {
    @XmlAttribute(name = "key")
    public String key;

    @XmlAttribute(name = "value")
    public String value;
}