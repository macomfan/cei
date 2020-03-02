package cn.ma.cei.model;

import cn.ma.cei.model.base.xElement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

public class xSDKDefinition extends xElement {
    @XmlAttribute(name = "document")
    public String document;
}
