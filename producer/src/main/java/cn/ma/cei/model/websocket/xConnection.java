package cn.ma.cei.model.websocket;

import cn.ma.cei.model.base.xElement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "connection_websocket")
@XmlRootElement(name = "connection")
public class xConnection extends xElement {
    @XmlAttribute(name = "url")
    public String url;

    @XmlAttribute(name="timeout")
    public String timeout;


}
