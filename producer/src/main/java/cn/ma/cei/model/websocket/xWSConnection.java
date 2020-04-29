package cn.ma.cei.model.websocket;

import cn.ma.cei.model.base.xElement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "connection_websocket")
@XmlRootElement(name = "connection")
public class xWSConnection extends xElement {
    @XmlAttribute(name = "url", required = true)
    public String url;

    @XmlAttribute(name = "timeout_s")
    public Integer timeout;

    @XmlElement(name = "open")
    public xWSOpen open;

    @XmlElement(name = "close")
    public xWSClose close;
}
