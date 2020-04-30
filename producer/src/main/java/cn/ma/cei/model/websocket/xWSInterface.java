package cn.ma.cei.model.websocket;

import cn.ma.cei.model.base.xElementWithInputs;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;


@XmlType(name = "interface_websocket")
@XmlRootElement(name = "interface")
public class xWSInterface extends xElementWithInputs {
    @XmlAttribute(name = "name", required = true)
    public String name;

    @XmlElement(name = "message")
    public xWSMessage message;

    @XmlElement(name = "event")
    public List<xWSUserCallback> events;
}
