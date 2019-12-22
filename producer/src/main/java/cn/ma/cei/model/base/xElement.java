package cn.ma.cei.model.base;


import cn.ma.cei.xml.XmlElementBase;

import javax.xml.bind.annotation.XmlAttribute;

@XmlElementBase
public class xElement {
    @XmlAttribute(name = "id")
    public String id;
}
