package cn.ma.cei.model;

import cn.ma.cei.model.base.xElement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sdk")
public class xSDK extends xElement {

    @XmlAttribute(name = "name")
    public String name;

    @XmlElement(name = "definition")
    public xSDKDefinition definition;

    @XmlElementWrapper(name = "models")
    @XmlElement(name = "model")
    public List<xModel> modelList;

//    @XmlElement(name = "authentications")
//    public xSDKAuthentications authentications;

    @XmlElement(name = "procedures")
    public xCustomProcedures procedures;

    @XmlElement(name = "clients")
    public xSDKClients clients;

    @Override
    public void customCheck() {
        super.customCheck();
        checkMemberNotNull(name, "exchange");
    }
}
