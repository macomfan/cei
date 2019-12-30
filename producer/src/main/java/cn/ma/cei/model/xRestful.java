package cn.ma.cei.model;

import cn.ma.cei.model.base.xElement;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "restful")
public class xRestful extends xElement {

    @XmlAttribute(name = "client_name")
    public String clientName;

    @XmlAttribute(name = "url")
    public String url;

    @XmlAttribute(name = "successful_code")
    public String successfulCode;

    @XmlAttribute(name = "timeout")
    public int timeout;

    @XmlElement(name = "interface")
    public List<xInterface> interfaceList;
}
