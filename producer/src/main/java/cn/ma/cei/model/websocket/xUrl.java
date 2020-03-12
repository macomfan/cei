package cn.ma.cei.model.websocket;

import cn.ma.cei.model.base.xAttributeExtension;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "url")
public class xUrl extends xAttributeExtension {
    @XmlAttribute
    public String target;
}
