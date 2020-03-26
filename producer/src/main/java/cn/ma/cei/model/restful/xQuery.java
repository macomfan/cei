package cn.ma.cei.model.restful;

import cn.ma.cei.model.base.xItemWithProcedure;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "query")
public class xQuery extends xItemWithProcedure {

    @XmlAttribute(name = "key")
    public String key;

    @XmlAttribute(name = "value")
    public String value;

    @XmlAttribute(name = "copy")
    public String copy;
}
