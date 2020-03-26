package cn.ma.cei.model.restful;

import cn.ma.cei.model.base.xItemWithProcedure;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "post_body")
public class xPostBody extends xItemWithProcedure {

    @XmlAttribute(name = "value")
    public String value;
}
