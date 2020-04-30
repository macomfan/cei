package cn.ma.cei.model.websocket;

import cn.ma.cei.model.base.xElement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "websockets")
public class xWebSocket extends xElement {

    @XmlAttribute(name = "name")
    public String name;

    @XmlElement(name = "connection")
    public xWSConnection connection;
    
    @XmlElementWrapper(name = "events")
    @XmlElement(name = "on_message")
    public List<xWSOnMessage> events;

    @XmlElementWrapper(name = "interfaces")
    @XmlElement(name = "interface")
    public List<xWSInterface> interfaces;
}
