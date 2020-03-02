package cn.ma.cei.model.websocket;

import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.xResponse;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class xCallback extends xElement {
    @XmlAttribute(name = "name")
    String name;

    @XmlElement(name = "trigger")
    xTrigger trigger;

    @XmlElement(name = "response")
    xResponse response;
}
