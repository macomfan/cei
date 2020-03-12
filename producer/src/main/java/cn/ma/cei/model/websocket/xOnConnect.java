package cn.ma.cei.model.websocket;

import cn.ma.cei.model.base.xElement;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "on_connect")
public class xOnConnect extends xElement {

    @XmlElement(name = "send")
    public xSend send;
}
