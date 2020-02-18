package cn.ma.cei.model.json;

import cn.ma.cei.model.base.xElement;

import javax.xml.bind.annotation.XmlAttribute;

public abstract class xJsonCheckerItem extends xElement {
    @XmlAttribute(name = "key")
    public String key;

    @XmlAttribute(name = "value")
    public String value;
}