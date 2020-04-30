package cn.ma.cei.model.websocket;

import cn.ma.cei.model.base.xElementWithInputs;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "open")
public class xWSOpen extends xElementWithInputs {

    @XmlElement(name = "connect")
    public xWSConnect connect;

    @XmlElement(name = "on_connect")
    public xWSSystemCallback onConnect;
}
