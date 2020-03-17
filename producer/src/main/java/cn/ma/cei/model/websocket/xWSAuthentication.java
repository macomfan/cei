package cn.ma.cei.model.websocket;

import cn.ma.cei.model.base.xElement;

import javax.xml.bind.annotation.XmlAttribute;

public class xWSAuthentication extends xElement {
    @XmlAttribute(name = "name")
    public String name;
}
