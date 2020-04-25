package cn.ma.cei.model.websocket;

import cn.ma.cei.model.xProcedure;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "send")
public class xSend extends xProcedure {

    @XmlAttribute(name = "value")
    public String value;
}
