package cn.ma.cei.model.websocket;

import cn.ma.cei.model.base.xElementWithInputs;

import javax.xml.bind.annotation.*;
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
