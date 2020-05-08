package cn.ma.cei.model;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.model.base.xElement;
import cn.ma.cei.utils.Checker;

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

    @XmlElement(name = "procedures")
    public xCustomProcedures procedures;

    @XmlElement(name = "clients")
    public xSDKClients clients;

    @Override
    public void customCheck() {
        super.customCheck();
        if (Checker.isEmpty(name)) {
            CEIErrors.showXMLFailure("name cannot be empty in SDK file: %s", filename);
        }
    }
}
