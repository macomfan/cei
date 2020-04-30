package cn.ma.cei.model.websocket;

import cn.ma.cei.model.xProcedure;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "url")
public class xUrl extends xProcedure {
    @XmlAttribute
    public String target;
}
