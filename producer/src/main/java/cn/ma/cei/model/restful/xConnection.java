package cn.ma.cei.model.restful;

import cn.ma.cei.model.base.xElement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "connection_restful")
@XmlRootElement(name = "connection")
public class xConnection extends xElement {

    @XmlAttribute(name = "url", required = true)
    public String url;

    @XmlAttribute(name = "timeout_s")
    public Integer timeout;
}
