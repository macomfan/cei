package cn.ma.cei.model.base;

import javax.xml.bind.annotation.XmlAttribute;

public abstract class xReferable extends xType {
    @XmlAttribute(name = "refer")
    public String refer;
}
