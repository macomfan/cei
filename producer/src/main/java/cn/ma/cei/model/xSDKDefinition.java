package cn.ma.cei.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "definition")
public class xSDKDefinition {
    @XmlAttribute(name = "document")
    public String document;
}
