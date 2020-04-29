package cn.ma.cei.model.processor;

import cn.ma.cei.model.base.xDataProcessorItem;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "send")
public class xWebSocketSend extends xDataProcessorItem {
    @XmlAttribute(name = "connection")
    public String connection;

    @XmlAttribute(name = "value", required = true)
    public String value;
}
