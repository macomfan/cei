package cn.ma.cei.model.restful;

import cn.ma.cei.model.base.xElement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "post_body")
public class xPostBody extends xElement {

    @XmlAttribute(name = "value")
    public String value;
}
