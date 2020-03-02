package cn.ma.cei.model.websocket;

import cn.ma.cei.model.base.xAttributeExtension;

import javax.xml.bind.annotation.XmlAttribute;

public class xSend extends xAttributeExtension {

    @XmlAttribute(name = "value")
    public String value;
}
