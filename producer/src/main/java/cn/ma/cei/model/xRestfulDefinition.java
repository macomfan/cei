package cn.ma.cei.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "definition")
public class xRestfulDefinition {

    @XmlAttribute(name = "url", required = true)
    public String url;
}
