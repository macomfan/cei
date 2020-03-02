package cn.ma.cei.model.websocket;

import cn.ma.cei.model.base.xElement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class xEvent extends xElement {

    @XmlAttribute(name = "name")
    String name;

    @XmlElement(name = "trigger")
    xTrigger trigger;

    @XmlElement(name = "send")
    xSend send;
}
