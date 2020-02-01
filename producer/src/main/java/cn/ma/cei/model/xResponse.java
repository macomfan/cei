package cn.ma.cei.model;

import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.json.xJsonParser;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class xResponse extends xElement {

    @XmlAttribute(name = "model")
    public String model;

    @XmlElement(name = "json_parser")
    public xJsonParser jsonParser;

    @Override
    public void customCheck() {
        super.customCheck();
        checkMemberNotNull(model, "model");
    }
}
