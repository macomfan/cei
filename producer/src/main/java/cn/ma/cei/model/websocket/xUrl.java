package cn.ma.cei.model.websocket;

import cn.ma.cei.model.base.xItemWithProcedure;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "url")
public class xUrl extends xItemWithProcedure {
    @XmlAttribute
    public String target;
}
