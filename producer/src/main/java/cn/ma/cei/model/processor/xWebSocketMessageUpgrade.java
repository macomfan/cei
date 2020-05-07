package cn.ma.cei.model.processor;

import cn.ma.cei.model.base.xDataProcessorItem;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "upgrade_websocket_message")
public class xWebSocketMessageUpgrade extends xDataProcessorItem {
    @XmlAttribute(name = "message")
    public String message;

    @XmlAttribute(name = "value", required = true)
    public String value;
}
