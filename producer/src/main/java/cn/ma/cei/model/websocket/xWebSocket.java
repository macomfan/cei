package cn.ma.cei.model.websocket;

import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.xResponse;
import cn.ma.cei.xml.CEIXmlAnyElementTypes;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "websockets")
public class xWebSocket extends xElement {

//    @XmlAttribute(name = "name")
//    public String name;
//
//    @XmlElement(name = "connection")
//    public xConnection connection;
//
//    @XmlElement(name = "event")
//    public List<xEvent> events;
//
//    @XmlAnyElement(lax = true)
//    @CEIXmlAnyElementTypes({xInterface.class})
//    public List<xInterface> interfaces;
}
