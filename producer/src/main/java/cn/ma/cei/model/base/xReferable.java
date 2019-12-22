package cn.ma.cei.model.base;

import cn.ma.cei.xml.XmlElementBase;

import javax.xml.bind.annotation.XmlAttribute;

@XmlElementBase
public abstract class xReferable extends xType {
    @XmlAttribute(name = "refer")
    public String refer;
}
