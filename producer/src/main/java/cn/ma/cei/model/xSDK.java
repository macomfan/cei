package cn.ma.cei.model;

import cn.ma.cei.model.base.xElement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sdk")
public class xSDK extends xElement {

    @XmlElement(name = "restful")
    public List<xRestful> restfulList;

    @XmlAttribute(name = "exchange")
    public String exchange;

    @XmlAttribute(name = "document")
    public String document;

    @XmlElement(name = "model")
    public List<xModel> modelList;

    @XmlElement(name = "signature")
    public List<xSignature> signatureList;
}
