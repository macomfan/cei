package cn.ma.cei.model;

import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.json.xJsonBuilder;
import cn.ma.cei.model.string.xStringBuilder;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "attribute")
public class xAttribute extends xElement {

    @XmlAttribute(name = "for")
    public String forAttribute;

    @XmlElement(name = "json_builder")
    public xJsonBuilder jsonBuilder;

    @XmlElement(name = "string_builder")
    public xStringBuilder stringBuilder;
}
