package cn.ma.cei.model.websocket;

import cn.ma.cei.model.base.xElement;
import cn.ma.cei.xml.CEIXmlAnyElementTypes;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "websockets")
public class xWebSocket extends xElement {

    @XmlAttribute(name = "name")
    public String name;

    @XmlElement(name = "connection")
    public xConnection connection;

    @XmlElementWrapper(name = "actions")
    @XmlElement(name = "action")
    public List<xAction> actions;

    @XmlElementWrapper(name = "interfaces")
    @XmlElement(name = "interface")
    public List<xInterface> interfaces;
}
