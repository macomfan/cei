package cn.ma.cei.model.websocket;

import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.base.xElementWithInputs;
import cn.ma.cei.model.xPreProcessor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "open")
public class xWSOpen extends xElementWithInputs {

    @XmlElement(name = "connect")
    public xWSConnect connect;

    @XmlElement(name = "on_connect")
    public xWSSystemCallback onConnect;
}
