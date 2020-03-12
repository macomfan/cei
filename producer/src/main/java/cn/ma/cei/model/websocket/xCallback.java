package cn.ma.cei.model.websocket;

import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.xResponse;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "callback")
public class xCallback extends xElement {
    @XmlAttribute(name = "name")
    public String name;

    @XmlElement(name = "trigger")
    public xTrigger trigger;

    @XmlElement(name = "response")
    public xResponse response;
}
