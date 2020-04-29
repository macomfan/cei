package cn.ma.cei.model.websocket;

import cn.ma.cei.model.xProcedure;
import cn.ma.cei.model.xResponse;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user_callback")
public class xWSUserCallback extends xProcedure {
    @XmlAttribute(name = "callback")
    public String callback;

    @XmlAttribute(name = "persistent")
    public boolean persistent;

    @XmlElement(name = "trigger")
    public xTrigger trigger;

    @XmlElement(name = "handler")
    public xResponse handler;
}
