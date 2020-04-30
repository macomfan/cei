package cn.ma.cei.model.restful;


import cn.ma.cei.model.base.xElementWithInputs;
import cn.ma.cei.model.xResponse;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "interface_restful")
@XmlRootElement(name = "interface")
public class xInterface extends xElementWithInputs {

    @XmlAttribute(name = "name")
    public String name;

    @XmlElement(name = "request")
    public xRequest request;

    @XmlElement(name = "response")
    public xResponse response;

    @Override
    public void customCheck() {
        super.customCheck();
        checkMemberNotNull(name, "name");
        checkMemberNotNull(response, "response");
    }
}
